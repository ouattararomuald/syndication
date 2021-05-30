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
package com.ouattararomuald.adapter.rxjava3;

import com.ouattararomuald.syndication.Syndication;
import com.ouattararomuald.syndication.http.HttpException;
import com.ouattararomuald.syndication.rss.RssFeed;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import java.io.IOException;
import java.net.InetAddress;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static okhttp3.mockwebserver.SocketPolicy.DISCONNECT_AFTER_REQUEST;

public final class ObservableTest {
  private static final String FAKE_URL = "/file.mp4";

  @Rule public final MockWebServer server = new MockWebServer();
  @Rule public final RecordingObserver.Rule observerRule = new RecordingObserver.Rule();

  interface Service {
    Observable<String> wrongType();

    Observable<RssFeed> readRssFeed();
  }

  private Service service;

  @BeforeEach public void setUp() throws Exception {
    server.start(InetAddress.getLocalHost(), 2507);
    Syndication syndication = new Syndication(
        server.url(FAKE_URL).toString(),
        new RxJava3CallAdapterFactory(),
        new OkHttpClient()
    );
    service = syndication.create(Service.class);
  }

  @AfterEach public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void wrongObservableType() {
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));

    RecordingObserver<String> observer = observerRule.create();
    Throwable throwable = Assertions.assertThrows(
        IllegalStateException.class,
        () -> service.wrongType().subscribe(observer)
    );
    assertThat(throwable.getMessage()).isEqualTo(
        "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>");
  }

  @Test public void readRssFeedSuccess200() {
    server.enqueue(new MockResponse().setResponseCode(200).setBody(Data.RSS_2_0_SPEC));

    RecordingObserver<RssFeed> observer = observerRule.create();
    service.readRssFeed().subscribe(observer);
    RssFeed value = observer.takeValue();
    assertThat(value).isNotNull();
  }

  @Test public void readRssFeedSuccess404() {
    server.enqueue(new MockResponse().setResponseCode(404));

    RecordingObserver<RssFeed> observer = observerRule.create();
    service.readRssFeed().subscribe(observer);
    observer.assertError(HttpException.class, "Unsuccessful request");
  }

  @Test public void readRssFeedFailure() {
    server.enqueue(new MockResponse().setSocketPolicy(DISCONNECT_AFTER_REQUEST));

    RecordingObserver<RssFeed> observer = observerRule.create();
    service.readRssFeed().subscribe(observer);
    observer.assertError(IOException.class);
  }

  @Test public void observableAssembly() {
    try {
      final Observable<RssFeed> rssFeed = Observable.just(Data.Companion.newRssFeed());
      RxJavaPlugins.setOnObservableAssembly(f -> rssFeed);
      assertThat(service.readRssFeed()).isNotNull();
    } finally {
      RxJavaPlugins.reset();
    }
  }

  @Test public void subscribeTwice() {
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));

    Observable<RssFeed> observable = service.readRssFeed();

    RecordingObserver<RssFeed> observer1 = observerRule.create();
    observable.subscribe(observer1);
    observer1.assertAnyValue().assertComplete();

    RecordingObserver<RssFeed> observer2 = observerRule.create();
    observable.subscribe(observer2);
    observer2.assertAnyValue().assertComplete();
  }
}
