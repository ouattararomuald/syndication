package com.ouattararomuald.adapter.kotlin.coroutines;

import com.ouattararomuald.syndication.atom.AtomFeed;
import com.ouattararomuald.syndication.rss.RssFeed;
import java.util.List;
import kotlinx.coroutines.Deferred;

public interface FeedReader {
  Deferred<AtomFeed> readAtom();

  Deferred<RssFeed> readRss();

  String readBadSimpleType();

  List<AtomFeed> readBadGenericType();

  Deferred readUnparametizedDeferred();

  Deferred<String> readParametizedDeferred();
}
