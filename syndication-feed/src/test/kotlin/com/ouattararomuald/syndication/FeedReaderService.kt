package com.ouattararomuald.syndication

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed

internal interface FeedReaderService {
  fun readAtom(): AtomFeed

  fun readRss(): RssFeed

  @Atom(returnClass = CustomRssFeed::class)
  @Rss(returnClass = CustomRssFeed::class)
  fun twoAnnotationsReadRss(): CustomRssFeed

  @Rss(returnClass = CustomRssFeed::class)
  fun customReadRss(): CustomRssFeed

  fun readBadType(): Int

  fun readBadGenericType(): List<AtomFeed>
}
