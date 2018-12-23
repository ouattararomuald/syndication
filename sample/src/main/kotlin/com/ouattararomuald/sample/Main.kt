package com.ouattararomuald.sample

import com.ouattararomuald.syndication.RssReader
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val rssReader = RssReader(url = "https://www.lequipe.fr/rss/actu_rss.xml")
  //val rssReader = RssReader(url = "https://api.travis-ci.org/repos/travis-ci/travis-ci/builds.atom")
  //val rssReader = RssReader(url = "https://www.thesitewizard.com/thesitewizard.xml")
  val syndicationFeed = rssReader.read()
  println(syndicationFeed)
}
