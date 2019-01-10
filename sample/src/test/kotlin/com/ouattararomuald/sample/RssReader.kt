package com.ouattararomuald.sample

import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.rss.RssFeed
import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single

interface RssReader {

  fun readAtom(): AtomFeed

  fun readRss(): RssFeed

  fun readFlowableRss(): Flowable<RssFeed>

  fun readMaybeRss(): Maybe<RssFeed>

  fun readSingleRss(): Single<RssFeed>
}