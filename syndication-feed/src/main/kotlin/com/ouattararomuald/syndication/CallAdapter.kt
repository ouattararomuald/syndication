package com.ouattararomuald.syndication

import okhttp3.Call
import org.simpleframework.xml.core.Persister
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

/**
 * Adapts a [Call] whose response is [SyndicationType] into the type of [Response].
 *
 * @param SyndicationType the type of syndication to be processed ([AtomFeed] or [RssFeed]).
 * @param Response type of the response returned by this adapter.
 */
abstract class CallAdapter<SyndicationType, Response> {

  /** Returns an instance of [Response] which delegates to [call]. */
  abstract fun adapt(call: Call, clazz: Class<SyndicationType>): Response

  protected fun parseXml(xml: String, clazz: Class<SyndicationType>): SyndicationType {
    val serializer = Persister()
    return serializer.read(clazz, xml)
  }

  /**
   * Creates [CallAdapter] instances based on the return type of [Syndication.create] methods.
   */
  abstract class Factory {

    /**
     * Returns a call adapter for interfaces methods that return type [returnType],
     * or null if can not be handled by this factory.
     */
    abstract fun get(returnType: Type): CallAdapter<*, *>?

    /**
     * Extract the upper bound of the generic parameter at `index` from `type`. For
     * example, index 1 of `Map<String, ? extends Runnable>` returns `Runnable`.
     */
    protected fun getParameterUpperBound(index: Int, type: ParameterizedType): Type {
      return Utils.getParameterUpperBound(index, type)
    }

    /**
     * Extract the raw class type from `type`. For example, the type representing
     * `List<? extends Runnable>` returns `List.class`.
     */
    protected fun getRawType(type: Type): Class<*> {
      return Utils.getRawType(type)
    }
  }
}
