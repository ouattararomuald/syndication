package com.ouattararomuald.sample

import com.ouattararomuald.syndication.rss.RssFeed
import kotlinx.coroutines.Deferred

interface RssReader {

  fun read(): Deferred<RssFeed>

  fun readSync(): RssFeed
}