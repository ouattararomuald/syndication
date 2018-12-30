package com.ouattararomuald.syndication

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Proxy
import java.lang.reflect.Type

/**
 * Adapts an interface to HTTP calls for reading **Atom 1.0** and/or **Rss 2.0**.
 *
 * Example:
 *
 * ```kotlin
 * interface MyReader {
 *   fun readAtom(): AtomFeed
 * }
 *
 * val syndicationReader = Syndication(url = "https://www.sample.com/rss/atom.xml")
 *
 * val myReader = syndicationReader.create(MyReader::class.java)
 * val atomFeed = myReader.readAtom()
 * ```
 *
 * @param url URL of the syndication feed.
 * @param callFactory call adapter factory.
 * @param httpClient instance of [OkHttpClient].
 */
class Syndication(
  private val url: String,
  private val callFactory: CallAdapter.Factory = DefaultCallAdapterFactory(),
  private val httpClient: OkHttpClient = OkHttpClient()
) {

  /**
   * Creates an implementation of the given [reader] interface.
   *
   * For example:
   *
   * ```kotlin
   * interface MyReader {
   *   fun readAtom(): AtomFeed
   * }
   *
   * val syndicationReader = Syndication(url = "https://www.sample.com/rss/atom.xml")
   *
   * val myReader = syndicationReader.create(MyReader::class.java)
   * val atomFeed = myReader.readAtom()
   * ```
   */
  @Suppress("UNCHECKED_CAST") fun <T> create(reader: Class<T>): T {
    return Proxy.newProxyInstance(reader.classLoader, arrayOf(reader),
        object : InvocationHandler {
          @Throws(Throwable::class)
          override fun invoke(proxy: Any, method: Method, args: Array<Any>?): Any {
            // If the method is a method from Object then defer to normal invocation.
            return if (method.declaringClass == Any::class.java) {
              method.invoke(this, args)
            } else {
              val returnType = method.genericReturnType

              val feedType = getFirstActualTypeArgument(returnType) ?: returnType
              validateReturnType(feedType!!)

              return read(
                  method.genericReturnType,
                  feedType,
                  httpClient,
                  callFactory,
                  url
              )
            }
          }
        }) as T
  }

  private fun getFirstActualTypeArgument(genericReturnType: Type): Type? {
    if (genericReturnType is ParameterizedType) {
      val arguments = genericReturnType.actualTypeArguments
      if (arguments.isNotEmpty()) {
        return arguments.first()
      }
    }
    return null
  }

  private fun validateReturnType(type: Type) {
    if (type != AtomFeed::class.java && type != RssFeed::class.java) {
      throw java.lang.IllegalStateException(
          "Return type must be AtomFeed, RssFeed or generic of both types: Deferred<AtomFeed>, Deferred<RssFeed>")
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun <T> read(
    returnType: Type,
    feedType: Type,
    httpClient: OkHttpClient,
    callFactory: CallAdapter.Factory,
    url: String
  ): T {
    return if (feedType == AtomFeed::class.java) {
      val readerAdapter: CallAdapter<AtomFeed, T> =
          callFactory.get(returnType) as? CallAdapter<AtomFeed, T>
              ?: throw IllegalStateException("CallAdapter == null")

      val request = Request.Builder().url(url).build()
      val call = httpClient.newCall(request)

      readerAdapter.adapt(call, AtomFeed::class.java)
    } else { // RSS_2_0
      val callAdapter: CallAdapter<RssFeed, T> =
          callFactory.get(returnType) as? CallAdapter<RssFeed, T>
              ?: throw IllegalStateException("CallAdapter == null")

      val request = Request.Builder().url(url).build()
      val call = httpClient.newCall(request)

      callAdapter.adapt(call, RssFeed::class.java)
    }
  }
}
