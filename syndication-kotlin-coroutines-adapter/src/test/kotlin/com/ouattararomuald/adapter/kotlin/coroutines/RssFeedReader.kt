package com.ouattararomuald.adapter.kotlin.coroutines

import com.ouattararomuald.syndication.rss.RssFeed
import kotlinx.coroutines.Deferred

internal interface RssFeedReader {

  fun read(): Deferred<RssFeed>
}