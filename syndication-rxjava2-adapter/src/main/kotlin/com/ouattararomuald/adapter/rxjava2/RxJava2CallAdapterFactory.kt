package com.ouattararomuald.adapter.rxjava2

import com.ouattararomuald.syndication.CallAdapter
import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import org.jetbrains.annotations.Nullable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxJava2CallAdapterFactory(
  @Nullable private val scheduler: Scheduler? = null,
  private val isAsync: Boolean = false
) : CallAdapter.Factory() {

  companion object {
    /**
     * Returns an instance which creates synchronous observables that do not operate on any scheduler
     * by default.
     */
    @JvmStatic
    fun create(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory(null, false)

    /**
     * Returns an instance which creates asynchronous observables. Applying
     * {@link Observable#subscribeOn} has no effect on stream types created by this factory.
     */
    @JvmStatic
    fun createAsync(): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory(null, true)

    /**
     * Returns an instance which creates synchronous observables that
     * {@linkplain Observable#subscribeOn(Scheduler) subscribe on} {@code scheduler} by default.
     */
    @JvmStatic
    fun createWithScheduler(scheduler: Scheduler): RxJava2CallAdapterFactory = RxJava2CallAdapterFactory(
        scheduler, true)
  }

  override fun get(returnType: Type): CallAdapter<*, *> {
    val rawType = getRawType(returnType)

    val isFlowable = rawType == Flowable::class.java
    val isSingle = rawType == Single::class.java
    val isMaybe = rawType == Maybe::class.java
    val isObservable = rawType == Observable::class.java

    val name = if (isFlowable) {
      "Flowable"
    } else if (isSingle) {
      "Single"
    } else if (isMaybe) {
      "Maybe"
    } else {
      "Observable"
    }

    if (!isObservable && !isFlowable && !isSingle && !isMaybe) {
      throw IllegalStateException("returnType must be $name")
    }

    if (returnType !is ParameterizedType) {
      throw IllegalStateException(
          "$name return type must be parameterized as $name<Foo> or $name<? extends Foo>")
    }

    val observableType = getParameterUpperBound(0, returnType)
    val rawObservableType = getRawType(observableType)

    return when (rawObservableType) {
      RssFeed::class.java -> RxJava2CallAdapter<RssFeed>(scheduler, isAsync, isFlowable, isSingle,
          isMaybe, RssFeed::class.java)
      AtomFeed::class.java -> RxJava2CallAdapter<AtomFeed>(scheduler, isAsync, isFlowable, isSingle,
          isMaybe, AtomFeed::class.java)
      else -> throw IllegalStateException("Unsupported type")
    }
  }
}