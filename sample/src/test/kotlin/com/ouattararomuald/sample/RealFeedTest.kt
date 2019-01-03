package com.ouattararomuald.sample

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Syndication
import org.junit.jupiter.api.Test

class RealFeedTest {

  private val atomFeedUrls = listOf(
      "https://www.senat.fr/rss/rapports.xml",
      "https://www.reddit.com/r/androiddev/.rss"
  )

  private val rssFeedUrls = listOf(
      "https://www.senat.fr/rss/rapports.rss",
      "https://www.lemonde.fr/rss/une.xml",
      "https://www.howtogeek.com/feed/",
      "https://rss.simplecast.com/podcasts/1684/rss"
  )

  @Test fun `atom feeds`() {
    atomFeedUrls.forEach {
      val syndication = Syndication(url = it)
      val reader = syndication.create(RssReader::class.java)
      assertThat(reader.readAtom()).isNotNull()
    }
  }

  @Test fun `rss feeds`() {
    rssFeedUrls.forEach {
      val syndication = Syndication(url = it)
      val reader = syndication.create(RssReader::class.java)
      val feed = reader.readRss()
      assertThat(feed).isNotNull()
    }
  }
}