/*
 * Copyright (C) 2018 Square, Inc.
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
import java.net.InetAddress;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class CancelDisposeTestSync {
  @Rule public final MockWebServer server = new MockWebServer();

  interface Service {
    Single<RssFeed> readRssFeed();
  }

  private final OkHttpClient client = new OkHttpClient();
  private Service service;

  @BeforeEach public void setUp() throws Exception {
    server.start(InetAddress.getLocalHost(), 2507);
    Syndication syndication = new Syndication(
        server.url("/").toString(),
        RxJava2CallAdapterFactory.create(),
        client
    );
    service = syndication.create(Service.class);
  }

  @AfterEach public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void disposeBeforeExecuteDoesNotEnqueue() {
    service.readRssFeed().test(true);
    assertEquals(0, server.getRequestCount());
  }
}
