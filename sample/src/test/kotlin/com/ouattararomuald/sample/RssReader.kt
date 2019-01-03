package com.ouattararomuald.sample

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed

interface RssReader {

  fun readAtom(): AtomFeed

  fun readRss(): RssFeed
}