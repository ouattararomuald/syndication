package com.ouattararomuald.syndication.rss

internal interface RssFeedReader {

  fun read(): RssFeed
}