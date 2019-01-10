/*
 * Copyright (C) 2015 Square, Inc.
 * Copyright (C) 2019 Romuald Ouattara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ouattararomuald.adapter.rxjava2;

import com.ouattararomuald.syndication.Syndication;
import com.ouattararomuald.syndication.rss.RssFeed;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TestRule;

import static com.google.common.truth.Truth.assertThat;

public final class SingleThrowingTest {
  @Rule public final MockWebServer server = new MockWebServer();
  @Rule public final TestRule resetRule = new RxJavaPluginsResetRule();
  @Rule public final RecordingSingleObserver.Rule subscriberRule =
      new RecordingSingleObserver.Rule();

  interface Service {
    Single<RssFeed> readRssFeed();
  }

  private Service service;

  @BeforeEach public void setUp() throws Exception {
    server.start(InetAddress.getLocalHost(), 2507);
    Syndication syndication = new Syndication(
        server.url("/").toString(),
        new RxJava2CallAdapterFactory(),
        new OkHttpClient()
    );
    service = syndication.create(Service.class);
  }

  @AfterEach public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void bodyThrowingInOnSuccessDeliveredToPlugin() {
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));

    final AtomicReference<Throwable> throwableRef = new AtomicReference<>();
    RxJavaPlugins.setErrorHandler(throwable -> {
      if (!throwableRef.compareAndSet(null, throwable.getCause())) {
        throw Exceptions.propagate(throwable);
      }
    });

    RecordingSingleObserver<RssFeed> observer = subscriberRule.create();
    final RuntimeException e = new RuntimeException();
    service.readRssFeed().subscribe(new ForwardingObserver<RssFeed>(observer) {
      @Override public void onSuccess(RssFeed value) {
        throw e;
      }
    });

    assertThat(throwableRef.get()).isSameAs(e);
  }

  @Test public void bodyThrowingInOnErrorDeliveredToPlugin() {
    server.enqueue(new MockResponse().setResponseCode(404));

    final AtomicReference<Throwable> throwableRef = new AtomicReference<>();
    RxJavaPlugins.setErrorHandler(throwable -> {
      if (!throwableRef.compareAndSet(null, throwable)) {
        throw Exceptions.propagate(throwable);
      }
    });

    RecordingSingleObserver<RssFeed> observer = subscriberRule.create();
    final AtomicReference<Throwable> errorRef = new AtomicReference<>();
    final RuntimeException e = new RuntimeException();
    service.readRssFeed().subscribe(new ForwardingObserver<RssFeed>(observer) {
      @Override public void onError(Throwable throwable) {
        if (!errorRef.compareAndSet(null, throwable)) {
          throw Exceptions.propagate(throwable);
        }
        throw e;
      }
    });

    //noinspection ThrowableResultOfMethodCallIgnored
    CompositeException composite = (CompositeException) throwableRef.get();
    assertThat(composite.getExceptions()).containsExactly(errorRef.get(), e);
  }

  private static abstract class ForwardingObserver<T> implements SingleObserver<T> {
    private final SingleObserver<T> delegate;

    ForwardingObserver(SingleObserver<T> delegate) {
      this.delegate = delegate;
    }

    @Override public void onSubscribe(Disposable disposable) {
      delegate.onSubscribe(disposable);
    }

    @Override public void onSuccess(T value) {
      delegate.onSuccess(value);
    }

    @Override public void onError(Throwable throwable) {
      delegate.onError(throwable);
    }
  }
}
