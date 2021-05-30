package com.ouattararomuald.adapter.rxjava3

import com.ouattararomuald.syndication.CallAdapter
import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.core.Single
import org.jetbrains.annotations.Nullable
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class RxJava3CallAdapterFactory(
  @Nullable private val scheduler: Scheduler? = null,
  private val isAsync: Boolean = false
) : CallAdapter.Factory() {

  companion object {
    /**
     * Returns an instance which creates synchronous observables that do not operate on any scheduler
     * by default.
     */
    @JvmStatic
    fun create(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory(null, false)

    /**
     * Returns an instance which creates asynchronous observables. Applying
     * {@link Observable#subscribeOn} has no effect on stream types created by this factory.
     */
    @JvmStatic
    fun createAsync(): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory(null, true)

    /**
     * Returns an instance which creates synchronous observables that
     * {@linkplain Observable#subscribeOn(Scheduler) subscribe on} {@code scheduler} by default.
     */
    @JvmStatic
    fun createWithScheduler(scheduler: Scheduler): RxJava3CallAdapterFactory = RxJava3CallAdapterFactory(
        scheduler, true)
  }

  override fun get(returnType: Type): CallAdapter<*, *> {
    val rawType = getRawType(returnType)

    val isFlowable = rawType == Flowable::class.java
    val isSingle = rawType == Single::class.java
    val isMaybe = rawType == Maybe::class.java
    val isObservable = rawType == Observable::class.java

    val name = when {
      isFlowable -> "Flowable"
      isSingle -> "Single"
      isMaybe -> "Maybe"
      else -> "Observable"
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
      RssFeed::class.java -> RxJava3CallAdapter<RssFeed>(
        scheduler, isAsync, isFlowable, isSingle,
        isMaybe, RssFeed::class.java
      )
      AtomFeed::class.java -> RxJava3CallAdapter<AtomFeed>(
        scheduler, isAsync, isFlowable, isSingle,
        isMaybe, AtomFeed::class.java
      )
      else -> throw IllegalStateException("Unsupported type")
    }
  }

  override fun <CustomReturnClass> get(
    returnType: Type,
    isCustomReturnType: Boolean,
    customReturnClass: Class<CustomReturnClass>
  ): CallAdapter<*, *> {
    val rawType = getRawType(returnType)

    val isFlowable = rawType == Flowable::class.java
    val isSingle = rawType == Single::class.java
    val isMaybe = rawType == Maybe::class.java
    val isObservable = rawType == Observable::class.java

    val name = when {
      isFlowable -> "Flowable"
      isSingle -> "Single"
      isMaybe -> "Maybe"
      else -> "Observable"
    }

    if (!isObservable && !isFlowable && !isSingle && !isMaybe) {
      throw IllegalStateException("returnType must be $name")
    }

    if (returnType !is ParameterizedType) {
      throw IllegalStateException(
          "$name return type must be parameterized as $name<Foo> or $name<? extends Foo>")
    }

    if (isCustomReturnType) {
      return RxJava3CallAdapter<CustomReturnClass>(
        scheduler, isAsync, isFlowable, isSingle,
        isMaybe, customReturnClass
      )
    }

    throw IllegalStateException("Unable to get adapter")
  }
}