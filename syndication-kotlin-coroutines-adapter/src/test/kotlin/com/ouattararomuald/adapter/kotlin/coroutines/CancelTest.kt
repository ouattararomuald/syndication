/**
 * Copyright 2017 Jake Wharton
 * Copyright 2018 Romuald Ouattara
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@file:Suppress("UNCHECKED_CAST")

package com.ouattararomuald.adapter.kotlin.coroutines

import com.google.common.reflect.TypeToken
import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.CallAdapter
import com.ouattararomuald.syndication.DeserializationException
import com.ouattararomuald.syndication.rss.RssFeed
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import okhttp3.Call
import org.junit.jupiter.api.Test
import java.io.IOException
import java.lang.reflect.Type

class CancelTest {
  companion object {
    const val FAKE_URL = "https://www.example.ci/file.mp4"
  }

  private val coroutineCallAdapterFactory = CoroutineCallAdapterFactory()

  @Test fun `no cancel on response`() {
    val type = typeOf<Deferred<RssFeed>>()
    val callAdapter =
        coroutineCallAdapterFactory.get(type) as CallAdapter<RssFeed, Deferred<RssFeed>>

    val call = CompletableCall()
    val deferredRssFeed = adapt(call, callAdapter)

    call.complete()

    assertThat(call.isCanceled).isFalse()
    assertThat(deferredRssFeed.isCompleted).isTrue()
  }

  @ExperimentalCoroutinesApi
  @Test fun `cancel on error`() {
    val type = typeOf<Deferred<RssFeed>>()
    val callAdapter =
        coroutineCallAdapterFactory.get(type) as CallAdapter<RssFeed, Deferred<RssFeed>>

    val call = CompletableCall()
    val deferredRssFeed = adapt(call, callAdapter)

    call.completeWithException(IOException())

    assertThat(call.isCanceled).isTrue()
    assertThat(deferredRssFeed.getCompletionExceptionOrNull()).isNotNull()
    assertThat(deferredRssFeed.isCancelled).isTrue()
  }

  @Test fun `cancel on cancel`() {
    val type = typeOf<Deferred<RssFeed>>()
    val callAdapter =
        coroutineCallAdapterFactory.get(type) as CallAdapter<RssFeed, Deferred<RssFeed>>

    val call = CompletableCall()
    val deferredRssFeed = adapt(call, callAdapter)

    assertThat(call.isCanceled).isFalse()
    deferredRssFeed.cancel()
    assertThat(call.isCanceled).isTrue()
  }

  @Test fun `cancel on deserialization error`() {
    val type = typeOf<Deferred<RssFeed>>()
    val callAdapter =
        coroutineCallAdapterFactory.get(type) as CallAdapter<RssFeed, Deferred<RssFeed>>

    val call = CompletableCall()
    val deferredRssFeed = adapt(call, callAdapter)

    call.complete(Data.UNDESERIALIZABLE_FEED)

    assertThat(call.isCanceled).isTrue()
    assertThat(deferredRssFeed.getCompletionExceptionOrNull()).isInstanceOf(
        DeserializationException::class.java)
    assertThat(deferredRssFeed.isCancelled).isTrue()
  }

  private inline fun <reified T, reified U> adapt(
    call: Call,
    callAdapter: CallAdapter<U, T>
  ): T = callAdapter.adapt(call, U::class.java)

  private inline fun <reified T> typeOf(): Type = object : TypeToken<T>() {}.type
}