package com.ouattararomuald.adapter.kotlin.coroutines

import com.ouattararomuald.syndication.CallAdapter
import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.http.HttpException
import com.ouattararomuald.syndication.rss.RssFeed
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Response
import java.io.IOException
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/** A [CallAdapter.Factory] for use with kotlin coroutines. */
class CoroutineCallAdapterFactory : CallAdapter.Factory() {

  override fun get(returnType: Type): CallAdapter<*, *>? {
    if (Deferred::class.java != getRawType(returnType)) {
      return null
    }
    if (returnType !is ParameterizedType) {
      throw IllegalStateException(
          "Deferred return type must be parameterized as Deferred<Foo> or Deferred<out Foo>")
    }
    val responseType = getParameterUpperBound(0, returnType)
    val rawDeferredType = getRawType(responseType)

    return when (rawDeferredType) {
      RssFeed::class.java -> ResponseAdapter<RssFeed>()
      AtomFeed::class.java -> ResponseAdapter<AtomFeed>()
      else -> throw IllegalStateException("Unsupported type")
    }
  }

  private inner class ResponseAdapter<SyndicationType> : CallAdapter<SyndicationType, Deferred<SyndicationType>>() {

    override fun adapt(call: Call, clazz: Class<SyndicationType>): Deferred<SyndicationType> {
      val deferred = CompletableDeferred<SyndicationType>()

      deferred.invokeOnCompletion {
        if (deferred.isCancelled) {
          call.cancel()
        }
      }

      call.enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
          deferred.completeExceptionally(e)
        }

        @Suppress("UNCHECKED_CAST")
        override fun onResponse(call: Call, response: Response) {
          if (response.isSuccessful) {
            deferred.complete(parseXml(response.body()!!.string(), clazz))
          } else {
            deferred.completeExceptionally(HttpException("Unsuccessful request"))
          }
        }
      })

      return deferred
    }
  }
}
