/*
 * Copyright (C) 2017 Square, Inc.
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
import com.ouattararomuald.syndication.rss.RssFeed;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.observers.TestObserver;
import java.io.IOException;
import java.net.InetAddress;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static okhttp3.mockwebserver.SocketPolicy.DISCONNECT_AFTER_REQUEST;
import static org.junit.Assert.assertFalse;

public final class AsyncTest {
  private static final String FAKE_URL = "/file.mp4";

  @Rule public final MockWebServer server = new MockWebServer();

  interface Service {
    Single<RssFeed> readRssFeed();
  }

  private Service service;

  @BeforeEach public void setUp() throws Exception {
    server.start(InetAddress.getLocalHost(), 2507);
    Syndication syndication = new Syndication(
        server.url(FAKE_URL).toString(),
        RxJava3CallAdapterFactory.createAsync(),
        new OkHttpClient()
    );
    service = syndication.create(Service.class);
  }

  @AfterEach public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void success() throws InterruptedException {
    TestObserver<RssFeed> observer = new TestObserver<>();
    service.readRssFeed().subscribe(observer);
    assertFalse(observer.await(1, SECONDS));

    server.enqueue(new MockResponse().setBody(Data.RSS_2_0_SPEC));
    observer.await(1, SECONDS);
    observer.assertComplete();
  }

  @Test public void failure() throws InterruptedException {
    TestObserver<RssFeed> observer = new TestObserver<>();
    service.readRssFeed().subscribe(observer);
    assertFalse(observer.await(1, SECONDS));

    server.enqueue(new MockResponse().setSocketPolicy(DISCONNECT_AFTER_REQUEST));
    observer.await(1, SECONDS);
    observer.assertError(IOException.class);
  }
}
