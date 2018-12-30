package com.ouattararomuald.syndication

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.http.HttpException
import com.ouattararomuald.syndication.rss.RssFeed
import okhttp3.Call
import java.lang.reflect.Type

internal class DefaultCallAdapterFactory : CallAdapter.Factory() {

  override fun get(returnType: Type): CallAdapter<*, *>? {
    val rawType = getRawType(returnType)

    if (RssFeed::class.java != rawType && AtomFeed::class.java != rawType) {
      return null
    }

    return when (returnType) {
      RssFeed::class.java -> ResponseAdapter<RssFeed>()
      AtomFeed::class.java -> ResponseAdapter<AtomFeed>()
      else -> throw IllegalStateException("Unsupported type") // Impossible
    }
  }

  private inner class ResponseAdapter<SyndicationType> : CallAdapter<SyndicationType, SyndicationType>() {

    override fun adapt(call: Call, clazz: Class<SyndicationType>): SyndicationType {
      val response = call.execute()
      if (response.isSuccessful) {
        return parseXml(response.body()!!.string(), clazz)
      } else {
        throw HttpException("Unsuccessful request")
      }
    }
  }
}
