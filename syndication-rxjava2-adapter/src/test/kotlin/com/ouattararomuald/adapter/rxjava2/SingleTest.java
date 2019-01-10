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
import com.ouattararomuald.syndication.http.HttpException;
import com.ouattararomuald.syndication.rss.RssFeed;
import io.reactivex.Single;
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

public final class SingleTest {
  private static final String FAKE_URL = "/file.mp4";

  @Rule public final MockWebServer server = new MockWebServer();
  @Rule public final RecordingSingleObserver.Rule observerRule = new RecordingSingleObserver.Rule();

  interface Service {
    Single<String> wrongType();

    Single<RssFeed> readRssFeed();
  }

  private Service service;

  @BeforeEach public void setUp() throws Exception {
    server.start(InetAddress.getLocalHost(), 2507);
    Syndication syndication = new Syndication(
        server.url(FAKE_URL).toString(),
        new RxJava2CallAdapterFactory(),
        new OkHttpClient()
    );
    service = syndication.create(Service.class);
  }

  @AfterEach public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void wrongObservableType() {
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));

    RecordingSingleObserver<String> subscriber = observerRule.create();
    Throwable throwable = Assertions.assertThrows(
        IllegalStateException.class,
        () -> service.wrongType().subscribe(subscriber)
    );
    assertThat(throwable.getMessage()).isEqualTo(
        "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>");
  }

  @Test public void bodySuccess200() {
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));

    RecordingSingleObserver<RssFeed> observer = observerRule.create();
    service.readRssFeed().subscribe(observer);
    assertThat(observer.assertAnyValue()).isNotNull();
  }

  @Test public void bodySuccess404() {
    server.enqueue(new MockResponse().setResponseCode(404));

    RecordingSingleObserver<RssFeed> observer = observerRule.create();
    service.readRssFeed().subscribe(observer);
    // Required for backwards compatibility.
    observer.assertError(HttpException.class, "Unsuccessful request");
  }

  @Test public void bodyFailure() {
    server.enqueue(new MockResponse().setSocketPolicy(DISCONNECT_AFTER_REQUEST));

    RecordingSingleObserver<RssFeed> observer = observerRule.create();
    service.readRssFeed().subscribe(observer);
    observer.assertError(IOException.class);
  }

  @Test public void subscribeTwice() {
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));
    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));

    Single<RssFeed> observable = service.readRssFeed();

    RecordingSingleObserver<Object> observer1 = observerRule.create();
    observable.subscribe(observer1);
    assertThat(observer1.assertAnyValue()).isNotNull();

    RecordingSingleObserver<Object> observer2 = observerRule.create();
    observable.subscribe(observer2);
    assertThat(observer2.assertAnyValue()).isNotNull();
  }
}

