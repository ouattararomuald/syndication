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
              //returnType::class.java.componentType

              val isCustomReturnType = isCustomReturnType(method)

              verifyCustomMethodAnnotations(method)

              val feedType = getFirstActualTypeArgument(returnType) ?: returnType
              if (!isCustomReturnType) {
                validateReturnType(feedType)
              } else {
                val annotations = getMethodAnnotations(method)
                val annotation = annotations.first()

                val kclass = when (annotation) {
                  is Atom -> annotation.returnClass
                  is Rss -> annotation.returnClass
                  else -> null
                }

                kclass?.let {
                  return read(
                      kclass.java,
                      method.genericReturnType,
                      feedType,
                      isCustomReturnType,
                      httpClient,
                      callFactory,
                      url
                  )
                }
              }

              return read(
                  RssFeed::class.java, // This params is not used for non-custom parsers
                  method.genericReturnType,
                  feedType,
                  isCustomReturnType,
                  httpClient,
                  callFactory,
                  url
              )
            }
          }
        }) as T
  }

  private fun verifyCustomMethodAnnotations(method: Method) {
    val annotations = getMethodAnnotations(method)

    if (annotations.size > 1) {
      throw IllegalStateException(
          "method ${method.name} cannot be annotated with both @Atom and @Rss")
    }
  }

  private fun isCustomReturnType(method: Method): Boolean {
    return method.isAnnotationPresent(Atom::class.java) || method.isAnnotationPresent(
        Rss::class.java)
  }

  private fun getMethodAnnotations(method: Method): Array<Annotation> {
    val arrayList = ArrayList<Annotation>()

    if (method.isAnnotationPresent(Atom::class.java)) {
      arrayList.add(method.getDeclaredAnnotation(Atom::class.java))
    }

    if (method.isAnnotationPresent(Rss::class.java)) {
      arrayList.add(method.getDeclaredAnnotation(Rss::class.java))
    }

    return arrayList.toTypedArray()
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
          "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>")
    }
  }

  @Suppress("UNCHECKED_CAST")
  private fun <T, R : Any> read(
    customReturnClass: Class<R>,
    returnType: Type,
    feedType: Type,
    isCustomParser: Boolean,
    httpClient: OkHttpClient,
    callFactory: CallAdapter.Factory,
    url: String
  ): T {
    return when (feedType) {
      AtomFeed::class.java -> {
        val callAdapter: CallAdapter<AtomFeed, T> =
            callFactory.get(returnType) as CallAdapter<AtomFeed, T>

        val request = Request.Builder().url(url).build()
        val call = httpClient.newCall(request)

        callAdapter.adapt(call, AtomFeed::class.java)
      }
      RssFeed::class.java -> {
        val callAdapter: CallAdapter<RssFeed, T> =
            callFactory.get(returnType) as CallAdapter<RssFeed, T>

        val request = Request.Builder().url(url).build()
        val call = httpClient.newCall(request)

        callAdapter.adapt(call, RssFeed::class.java)
      }
      else -> {
        val callAdapter: CallAdapter<R, T> =
            callFactory.get(returnType, isCustomParser, customReturnClass) as CallAdapter<R, T>

        val request = Request.Builder().url(url).build()
        val call = httpClient.newCall(request)

        callAdapter.adapt(call, customReturnClass)
      }
    }
  }
}
