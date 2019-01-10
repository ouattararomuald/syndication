package com.ouattararomuald.sample

import com.ouattararomuald.syndication.rss.RssFeed
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import kotlinx.coroutines.Deferred

interface RssReader {

  fun read(): Deferred<RssFeed>

  fun readSync(): RssFeed

  fun readFlowable(): Flowable<RssFeed>

  fun readMaybe(): Maybe<RssFeed>

  fun readObservable(): Observable<RssFeed>

  fun readSingle(): Single<RssFeed>
}