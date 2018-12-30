package com.ouattararomuald.syndication.atom

internal interface AtomFeedReader {

  fun read(): AtomFeed
}