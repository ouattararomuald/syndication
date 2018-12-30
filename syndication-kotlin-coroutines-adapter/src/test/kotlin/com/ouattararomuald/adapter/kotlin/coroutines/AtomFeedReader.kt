package com.ouattararomuald.adapter.kotlin.coroutines

import com.ouattararomuald.syndication.atom.AtomFeed
import kotlinx.coroutines.Deferred

internal interface AtomFeedReader {

  fun read(): Deferred<AtomFeed>
}