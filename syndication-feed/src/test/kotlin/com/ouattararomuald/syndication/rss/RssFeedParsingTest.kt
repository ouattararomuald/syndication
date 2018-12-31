package com.ouattararomuald.syndication.rss

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Data
import com.ouattararomuald.syndication.FeedReaderService
import com.ouattararomuald.syndication.Syndication
import com.ouattararomuald.syndication.atom.AtomLink
import com.ouattararomuald.syndication.runTests
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RssFeedParsingTest {

  companion object {
    const val FAKE_URL = "/file.mp4"

    private lateinit var globalServer: MockWebServer
    private lateinit var syndicationFeed: RssFeed
    private lateinit var channel: Channel

    @JvmStatic
    @BeforeAll
    fun globalSetUp() {
      globalServer = MockWebServer()
      globalServer.enqueue(
          MockResponse()
              .setResponseCode(200)
              .setBody(Data.RSS_2_0_SPEC)
      )
      globalServer.runTests {
        val baseUrl = globalServer.url(FAKE_URL)

        val reader = Syndication(baseUrl.toString()).create(FeedReaderService::class.java)
        syndicationFeed = reader.readRss()
        channel = syndicationFeed.channel
      }
      globalServer.close()
    }
  }

  private lateinit var server: MockWebServer

  @BeforeEach fun setUp() {
    server = MockWebServer()
  }

  @AfterEach fun tearDown() {
    server.close()
  }

  @Test
  fun `deserialize rss feed`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(FeedReaderService::class.java)
      val syndicationFeed = reader.readRss()

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test
  fun `verify title`() {
    assertThat(channel.title).isEqualTo("Liftoff News")
  }

  @Test
  fun `verify link`() {
    assertThat(channel.links).isEqualTo(
        listOf(AtomLink().apply { value = "http://liftoff.msfc.nasa.gov/" }))
  }

  @Test
  fun `verify description`() {
    assertThat(channel.description).isEqualTo("Liftoff to Space Exploration.")
  }

  @Test
  fun `verify language`() {
    assertThat(channel.language).isEqualTo("en-us")
  }

  @Test
  fun `verify published`() {
    assertThat(channel.published).isEqualTo("Tue, 10 Jun 2003 04:00:00 GMT")
  }

  @Test
  fun `verify last update`() {
    assertThat(channel.lastUpdatedTime).isEqualTo("Tue, 10 Jun 2003 09:41:01 GMT")
  }

  @Test
  fun `verify generator`() {
    assertThat(channel.generator).isEqualTo("Weblog Editor 2.0")
  }

  @Test
  fun `verify items`() {
    val items = listOf(
        Item().apply {
          title = "Star City"
          link = "http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp"
          description =
              "How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>."
          guid = "http://liftoff.msfc.nasa.gov/2003/06/03.html#item573"
          published = "Tue, 03 Jun 2003 09:39:21 GMT"
        },
        Item().apply {
          description =
              "Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a <a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\">partial eclipse of the Sun</a> on Saturday, May 31st."
          published = "Fri, 30 May 2003 11:06:42 GMT"
          guid = "http://liftoff.msfc.nasa.gov/2003/05/30.html#item572"
        }
    )

    assertThat(channel.items).isEqualTo(items)
  }
}