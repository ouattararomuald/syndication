package com.ouattararomuald.sample

import com.ouattararomuald.syndication.Rss
import io.reactivex.Single
import kotlinx.coroutines.Deferred

interface CustomReader {

  @Rss(returnClass = CustomRssFeed::class)
  fun read(): CustomRssFeed

  @Rss(returnClass = CustomRssFeed::class)
  fun readAsync(): Deferred<CustomRssFeed>

  @Rss(returnClass = CustomRssFeed::class)
  fun readOnce(): Single<CustomRssFeed>
}