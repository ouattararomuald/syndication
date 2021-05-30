package com.ouattararomuald.sample

import com.ouattararomuald.syndication.rss.RssFeed
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.Deferred

interface RssReaderRxJava3 {

  fun read(): Deferred<RssFeed>

  fun readSync(): RssFeed

  fun readFlowable(): Flowable<RssFeed>

  fun readMaybe(): Maybe<RssFeed>

  fun readObservable(): Observable<RssFeed>

  fun readOnce(): Single<RssFeed>
}