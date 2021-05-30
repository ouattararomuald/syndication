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
package com.ouattararomuald.adapter.rxjava3;

import com.ouattararomuald.adapter.rxjava3.RxJava3CallAdapterFactory;
import com.ouattararomuald.syndication.Syndication;
import com.ouattararomuald.syndication.rss.RssFeed;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import java.net.InetAddress;
import java.util.List;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Rule;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class CancelDisposeTest {
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
        RxJava3CallAdapterFactory.createAsync(),
        client
    );
    service = syndication.create(Service.class);
  }

  @AfterEach public void tearDown() throws Exception {
    server.shutdown();
  }

  @Test public void disposeCancelsCall() {
    Disposable disposable = service.readRssFeed().subscribe();
    List<Call> calls = client.dispatcher().runningCalls();
    assertEquals(1, calls.size());
    disposable.dispose();
    assertTrue(calls.get(0).isCanceled());
  }

  @Test public void disposeBeforeEnqueueDoesNotEnqueue() {
    service.readRssFeed().test(true);
    List<Call> calls = client.dispatcher().runningCalls();
    assertEquals(0, calls.size());
  }

  @Test public void cancelDoesNotDispose() {
    Disposable disposable = service.readRssFeed().subscribe();
    List<Call> calls = client.dispatcher().runningCalls();
    assertEquals(1, calls.size());
    calls.get(0).cancel();
    assertFalse(disposable.isDisposed());
  }
}
