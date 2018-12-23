package com.ouattararomuald.syndication

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.http.executeRequest
import com.ouattararomuald.syndication.rss.RssFeed
import okhttp3.OkHttpClient
import okhttp3.Request
import org.simpleframework.xml.core.Persister
import java.io.IOException

/**
 * Reads and parse the syndication feed at the given URL;
 *
 * The feed must be **Atom 1.0** or **Rss 2.0**.
 */
class RssReader(private val url: String) {

  companion object {
    internal const val CONTENT_TYPE = "content-type"
    internal const val ATOM = "application/atom+xml"
    internal const val RSS = "application/rss+xml"
  }

  private val httpClient = OkHttpClient()

  /**
   * Reads the feed at the [url] associated with this reader.
   *
   * @throws DeserializationException when deserialization failed.
   */
  suspend fun read(): SyndicationFeed? {
    try {
      val contentType = readContentType(url)

      return when (contentType) {
        RSS -> read(RssFeed::class.java)?.toSyndicationFeed()
        ATOM -> read(AtomFeed::class.java)?.toSyndicationFeed()
        else -> null
      }
    } catch (e: DeserializationException) {
      throw e
    } catch (e: IOException) {
      throw e
    }
  }

  internal suspend fun <T> read(clazz: Class<T>): T? {

    try {
      val request = Request.Builder().url(url).build()

      val response = httpClient.newCall(request).executeRequest()

      if (response.isSuccessful) {
        response.body()?.let {
          try {
            return parseResponse(it.string(), clazz)
          } catch (e: Exception) {
            throw DeserializationException(
                message = "object cannot be fully deserialized",
                cause = e
            )
          }
        }
      }
    } catch (e: IOException) {
      throw e
    }

    return null
  }

  private suspend fun readContentType(url: String): String? {
    val request = Request.Builder().url(url).head().build()

    val response = httpClient.newCall(request).executeRequest()

    if (response.isSuccessful) {
      if (response.headers().toMultimap().containsKey(CONTENT_TYPE)) {
        return response.header(CONTENT_TYPE)
      }
    }

    return null
  }

  private fun <T> parseResponse(xml: String, clazz: Class<T>): T {
    val serializer = Persister()
    return serializer.read(clazz, xml)
  }
}
