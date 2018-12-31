package com.ouattararomuald.syndication

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed

internal interface FeedReaderService {
  fun readAtom(): AtomFeed

  fun readRss(): RssFeed

  fun readBadType(): Int

  fun readBadGenericType(): List<AtomFeed>
}
