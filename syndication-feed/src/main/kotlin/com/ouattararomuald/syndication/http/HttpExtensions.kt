@file:JvmName("HttpUtils")
package com.ouattararomuald.syndication.http

import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

internal suspend fun Call.executeRequest(): Response = suspendCoroutine {cont->
  enqueue(object : Callback {
    override fun onFailure(call: Call, e: IOException) {
      cont.resumeWithException(e)
    }

    override fun onResponse(call: Call, response: Response) {
      cont.resume(response)
    }
  })
}
