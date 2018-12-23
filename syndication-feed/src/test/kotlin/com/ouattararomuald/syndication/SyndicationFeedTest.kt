package com.ouattararomuald.syndication

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class SyndicationFeedTest {

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
  fun `deserializing Rss_2_0 feed should success`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test
  fun `deserializing Atom_1_0 feed should success`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test
  fun `Atom_1_0 verify id`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.id).isEqualTo("FeedID")
    }
  }

  @Test
  fun `Atom_1_0 verify title`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.title).isEqualTo("Feed Title")
    }
  }

  @Test
  fun `Atom_1_0 verify description`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.description).isEqualTo("This is a sample feed")
    }
  }

  @Test
  fun `Atom_1_0 verify last update`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.lastUpdatedTime).isEqualTo("2007-04-13T17:29:38Z")
    }
  }

  @Test
  fun `Atom_1_0 verify documentation`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.documentation).isEqualTo("")
    }
  }

  @Test
  fun `Atom_1_0 verify items`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.ATOM)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      val items = listOf(SyndicationItem(
          id = "ItemID",
          title = "Item Title",
          lastUpdatedTime = "2007-04-13T17:29:38Z",
          content = Content(type = "text").apply { value = "Some text content" },
          links = listOf(Link(href = "http://contoso/items")),
          summary = Summary().apply { value = "Some text." },
          published = "",
          copyright = Copyright(type = ""),
          source = ""
      ))

      assertThat(syndicationFeed?.items).isEqualTo(items)
    }
  }

  @Test
  fun `Rss_2_0 verify id`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.id).isEqualTo("")
    }
  }

  @Test
  fun `Rss_2_0 verify title`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.title).isEqualTo("Liftoff News")
    }
  }

  @Test
  fun `Rss_2_0 verify description`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.description).isEqualTo("Liftoff to Space Exploration.")
    }
  }

  @Test
  fun `Rss_2_0 verify last update`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.lastUpdatedTime).isEqualTo("Tue, 10 Jun 2003 09:41:01 GMT")
    }
  }

  @Test
  fun `Rss_2_0 verify documentation`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      assertThat(syndicationFeed?.documentation).isEqualTo("http://blogs.law.harvard.edu/tech/rss")
    }
  }

  @Test
  fun `Rss_2_0 verify items`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setHeader(RssReader.CONTENT_TYPE, RssReader.RSS)
            .setBody(Data.RSS_2_0_SPEC)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read() }

      val items = listOf(SyndicationItem(
          id = "http://liftoff.msfc.nasa.gov/2003/06/03.html#item573",
          title = "Star City",
          lastUpdatedTime = "Tue, 03 Jun 2003 09:39:21 GMT",
          content = Content().apply {
            value =
                "How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>."
          },
          links = listOf(Link(href = "http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp")),
          summary = Summary().apply {
            value =
                "How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's <a href=\"http://howe.iki.rssi.ru/GCTC/gctc_e.htm\">Star City</a>."
          },
          published = "Tue, 03 Jun 2003 09:39:21 GMT",
          copyright = Copyright(type = ""),
          source = ""
      ), SyndicationItem(
          id = "http://liftoff.msfc.nasa.gov/2003/05/30.html#item572",
          title = "",
          lastUpdatedTime = "Fri, 30 May 2003 11:06:42 GMT",
          content = Content().apply {
            value =
                "Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a <a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\">partial eclipse of the Sun</a> on Saturday, May 31st."
          },
          links = listOf(Link(href = "")),
          summary = Summary().apply {
            value =
                "Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a <a href=\"http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm\">partial eclipse of the Sun</a> on Saturday, May 31st."
          },
          published = "Fri, 30 May 2003 11:06:42 GMT",
          copyright = Copyright(type = ""),
          source = ""
      ))

      assertThat(syndicationFeed?.items).isEqualTo(items)
    }
  }
}