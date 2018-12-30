package com.ouattararomuald.syndication.rss

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Data
import com.ouattararomuald.syndication.Syndication
import com.ouattararomuald.syndication.atom.AtomLink
import com.ouattararomuald.syndication.runTests
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class RssFeedParsingTest {

  companion object {
    const val FAKE_URL = "/file.mp4"
  }

  private lateinit var server: MockWebServer

  @BeforeEach fun setUp() {
    server = MockWebServer()
  }

  @AfterEach fun tearDown() {
    server.close()
  }

  @Test
  fun `Rss_2_0 deserializing feed should success`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val syndicationFeed = reader.read()

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test
  fun `Rss_2_0 title`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.title).isEqualTo("Liftoff News")
    }
  }

  @Test
  fun `Rss_2_0 link`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.links).isEqualTo(
          listOf(AtomLink().apply { value = "http://liftoff.msfc.nasa.gov/" }))
    }
  }

  @Test
  fun `Rss_2_0 description`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.description).isEqualTo("Liftoff to Space Exploration.")
    }
  }

  @Test
  fun `Rss_2_0 language`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.language).isEqualTo("en-us")
    }
  }

  @Test
  fun `Rss_2_0 published`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.published).isEqualTo("Tue, 10 Jun 2003 04:00:00 GMT")
    }
  }

  @Test
  fun `Rss_2_0 last update`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.lastUpdatedTime).isEqualTo("Tue, 10 Jun 2003 09:41:01 GMT")
    }
  }

  @Test
  fun `Rss_2_0 generator`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

      assertThat(channel.generator).isEqualTo("Weblog Editor 2.0")
    }
  }

  @Test
  fun `Rss_2_0 items`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(RssFeedReader::class.java)
      val channel = reader.read().channel

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
}