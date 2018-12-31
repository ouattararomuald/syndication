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
package com.ouattararomuald.adapter.kotlin.coroutines

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okio.Timeout
import java.io.IOException
import java.io.InterruptedIOException
import java.util.concurrent.CountDownLatch

internal class CompletableCall : Call {
  private var response: Response? = null

  private val lock = Any()
  private var executed = false
  private var canceled = false
  private var callback: Callback? = null
  private val done = CountDownLatch(1)
  private var exception: Throwable? = null

  fun complete() = complete(createMockResponse(Data.RSS_2_0_SPEC))

  fun complete(response: Response) {
    synchronized(lock) {
      this.response = response
      callback?.onResponse(this, response)
    }
    done.countDown()
  }

  fun completeWithException(t: Throwable) {
    synchronized(lock) {
      exception = t
      callback?.onFailure(this, IOException(t))
    }
    done.countDown()
  }

  override fun request(): Request = response!!.request()

  override fun isExecuted(): Boolean = synchronized(lock) { executed }

  override fun isCanceled(): Boolean = synchronized(lock) { canceled }

  override fun timeout(): Timeout {
    TODO("not implemented")
  }

  override fun clone(): Call = CompletableCall()

  override fun cancel() {
    synchronized(lock) {
      if (canceled) return
      canceled = true

      val exception = InterruptedIOException("canceled")
      this.exception = exception
      callback?.onFailure(this, exception)
    }
    done.countDown()
  }

  override fun enqueue(callback: Callback) {
    synchronized(lock) {
      if (exception != null) {
        callback.onFailure(this, IOException(exception))
      } else if (response != null) {
        callback.onResponse(this, response!!)
      } else {
        this.callback = callback
      }
    }
  }

  override fun execute(): Response {
    synchronized(lock) {
      check(!executed) { "Already executed " }
      executed = true
    }
    done.await()
    synchronized(lock) {
      if (exception != null) {
        throw exception!!
      }
      return response!!
    }
  }

  private fun createMockResponse(stringResponse: String, successful: Boolean = true): Response {
    return Response.Builder()
        .apply {
          body(ResponseBody.create(null, stringResponse))
          code(if (successful) 200 else 500)
          message("OK 200")
          protocol(Protocol.HTTP_1_1)
          request(Request.Builder().url(CancelTest.FAKE_URL).build())
        }
        .build()
  }
}