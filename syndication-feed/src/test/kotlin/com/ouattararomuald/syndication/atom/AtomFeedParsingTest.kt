package com.ouattararomuald.syndication.atom

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Author
import com.ouattararomuald.syndication.Content
import com.ouattararomuald.syndication.Contributor
import com.ouattararomuald.syndication.Data
import com.ouattararomuald.syndication.FeedReaderService
import com.ouattararomuald.syndication.Link
import com.ouattararomuald.syndication.Syndication
import com.ouattararomuald.syndication.runTests
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class AtomFeedParsingTest {

  companion object {
    const val FAKE_URL = "/file.mp4"

    private lateinit var globalServer: MockWebServer
    private lateinit var syndicationFeed: AtomFeed

    @JvmStatic
    @BeforeAll
    fun globalSetUp() {
      globalServer = MockWebServer()
      globalServer.enqueue(
          MockResponse()
              .setResponseCode(200)
              .setBody(Data.ATOM_1_0)
      )
      globalServer.runTests {
        val baseUrl = globalServer.url(FAKE_URL)

        val reader = Syndication(baseUrl.toString()).create(FeedReaderService::class.java)
        syndicationFeed = reader.readAtom()
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
  fun `deserialize atom feed`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(baseUrl.toString()).create(FeedReaderService::class.java)
      val syndicationFeed = reader.readAtom()

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test
  fun `verify id`() {
    assertThat(syndicationFeed.id).isEqualTo("tag:example.org,2003:3")
  }

  @Test
  fun `verify title`() {
    assertThat(syndicationFeed.title.value).isEqualTo("dive into mark")
  }

  @Test
  fun `verify title type`() {
    assertThat(syndicationFeed.title.type).isEqualTo("text")
  }

  @Test
  fun `verify subtitle`() {
    assertThat(syndicationFeed.subtitle?.value).isEqualTo(
        "A <em>lot</em> of effort went into making this effortless"
    )
  }

  @Test
  fun `verify subtitle type`() {
    assertThat(syndicationFeed.subtitle?.type).isEqualTo("html")
  }

  @Test
  fun `verify last update time`() {
    assertThat(syndicationFeed.lastUpdatedTime).isEqualTo("2005-07-31T12:29:29Z")
  }

  @Test
  fun `verify links`() {
    assertThat(syndicationFeed.links).isEqualTo(listOf(
        Link("http://example.org/", rel = "alternate", type = "text/html", hreflang = "en"),
        Link("http://example.org/feed.atom", rel = "self", type = "application/atom+xml")
    ))
  }

  @Test
  fun `verify rights`() {
    assertThat(syndicationFeed.copyright?.value).isEqualTo("Copyright (c) 2003, Mark Pilgrim")
  }

  @Test
  fun `verify rights type`() {
    assertThat(syndicationFeed.copyright?.type).isNull()
  }

  @Test
  fun `verify logo`() {
    assertThat(syndicationFeed.logo).isEqualTo("http://example.com/image.jpg")
  }

  @Test
  fun `verify generator`() {
    assertThat(syndicationFeed.generator?.value).isEqualTo("Example Toolkit")
  }

  @Test
  fun `verify generator type`() {
    assertThat(syndicationFeed.generator?.uri).isEqualTo("http://www.example.com/")
  }

  @Test
  fun `verify generator version`() {
    assertThat(syndicationFeed.generator?.version).isEqualTo("1.0")
  }

  @Test
  fun `verify language`() {
    assertThat(syndicationFeed.language).isEqualTo("en-us")
  }

  @Test
  fun `verify base uri`() {
    assertThat(syndicationFeed.baseUri).isEqualTo("http://example.org/today/")
  }

  @Test
  fun `verify categories`() {
    assertThat(syndicationFeed.categories).isEqualTo(listOf(AtomCategory(
        term = "FeedCategory",
        scheme = "CategoryScheme",
        label = "CategoryLabel"
    )))
  }

  @Test
  fun `verify authors`() {
    val authors = listOf(
        Author(name = "Mark Pilgrim", uri = "http://example.org/", email = "f8dy@example.com")
    )

    assertThat(syndicationFeed.authors).isEqualTo(authors)
  }

  @Test
  fun `verify contributors`() {
    val contributors = listOf(Contributor(name = "Sam Ruby"))

    assertThat(syndicationFeed.contributors).isEqualTo(contributors)
  }

  @Test
  fun `verify items`() {
    val items = listOf(Entry(
        id = "tag:example.org,2003:3.2397",
        title = "Atom draft-07 snapshot",
        lastUpdatedTime = "2005-07-31T12:29:29Z",
        published = "2003-12-13T08:29:29-04:00",
        links = listOf(
            Link("http://example.org/2005/04/02/atom", rel = "alternate", type = "text/html"),
            Link("http://example.org/audio/ph34r_my_podcast.mp3", rel = "enclosure",
                type = "audio/mpeg", length = 1337)
        ),
        authors = listOf(
            Author(name = "Mark Pilgrim", uri = "http://example.org/", email = "f8dy@example.com")
        ),
        contributors = listOf(Contributor(name = "Sam Ruby"), Contributor(name = "Joe Gregorio")),
        content = Content(type = "xhtml",
            value = """<div xmlns="http://www.w3.org/1999/xhtml"><p><i>[Update: The Atom draft is finished.]</i></p></div>"""),
        categories = listOf(AtomCategory(
            term = "FeedCategory",
            scheme = "CategoryScheme",
            label = "CategoryLabel"
        ))
    ))

    assertThat(syndicationFeed.items).isEqualTo(items)
  }
}