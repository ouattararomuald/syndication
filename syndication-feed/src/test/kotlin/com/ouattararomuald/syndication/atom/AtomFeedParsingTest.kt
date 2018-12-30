package com.ouattararomuald.syndication.atom

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Author
import com.ouattararomuald.syndication.Content
import com.ouattararomuald.syndication.Contributor
import com.ouattararomuald.syndication.Data
import com.ouattararomuald.syndication.FeedReaderService
import com.ouattararomuald.syndication.Summary
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
  fun `Atom_1_0 deserializing feed should success`() {
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
  fun `Atom_1_0 verify id`() {
    assertThat(syndicationFeed.id).isEqualTo("FeedID")
  }

  @Test
  fun `Atom_1_0 verify title`() {
    assertThat(syndicationFeed.title.value).isEqualTo("Feed Title")
  }

  @Test
  fun `Atom_1_0 verify title type`() {
    assertThat(syndicationFeed.title.type).isEqualTo("text")
  }

  @Test
  fun `Atom_1_0 verify rights`() {
    assertThat(syndicationFeed.copyright.value).isEqualTo("Copyright 2007")
  }

  @Test
  fun `Atom_1_0 verify rights type`() {
    assertThat(syndicationFeed.copyright.type).isEqualTo("text")
  }

  @Test
  fun `Atom_1_0 verify description`() {
    assertThat(syndicationFeed.description.value).isEqualTo("This is a sample feed")
  }

  @Test
  fun `Atom_1_0 verify description type`() {
    assertThat(syndicationFeed.description.type).isEqualTo("text")
  }

  @Test
  fun `Atom_1_0 verify last update time`() {
    assertThat(syndicationFeed.lastUpdatedTime).isEqualTo("2007-04-13T17:29:38Z")
  }

  @Test
  fun `Atom_1_0 verify logo`() {
    assertThat(syndicationFeed.logo).isEqualTo("http://contoso/image.jpg")
  }

  @Test
  fun `Atom_1_0 verify generator`() {
    assertThat(syndicationFeed.generator).isEqualTo("Sample Code")
  }

  @Test
  fun `Atom_1_0 verify language`() {
    assertThat(syndicationFeed.language).isEqualTo("en-us")
  }

  @Test
  fun `Atom_1_0 verify base uri`() {
    assertThat(syndicationFeed.baseUri).isEqualTo("http://example.org/today/")
  }

  @Test
  fun `Atom_1_0 verify links`() {
    assertThat(syndicationFeed.links).isEqualTo(listOf(
        AtomLink("http://contoso/link").apply {
          rel = "alternate"
          type = "text/html"
          title = "AtomLink Title"
          length = 1000
        }, AtomLink("http://example.org/feed.atom").apply {
      rel = "self"
      type = "application/atom+xml"
    }))
  }

  @Test
  fun `Atom_1_0 verify categories`() {
    val category = AtomCategory(term = "FeedCategory")
    category.scheme = "CategoryScheme"
    category.label = "CategoryLabel"
    val categories = listOf(category)

    assertThat(syndicationFeed.categories).isEqualTo(categories)
  }

  @Test
  fun `Atom_1_0 verify authors`() {
    val authors = listOf(Author("Jesper Aaberg").apply {
      uri = "http://contoso/Aaberg"
      email = "Jesper.Asberg@contoso.com"
    })

    assertThat(syndicationFeed.authors).isEqualTo(authors)
  }

  @Test
  fun `Atom_1_0 verify contributors`() {
    val contributors =
        listOf(Contributor("Lene Aalling", "http://contoso/Aalling", "Lene.Aaling@contoso.com"))

    assertThat(syndicationFeed.contributors).isEqualTo(contributors)
  }

  @Test
  fun `Atom_1_0 verify items`() {
    val items = listOf(AtomItem(
        "ItemID",
        "Item Title",
        "2007-04-13T17:29:38Z"
    ).apply {
      links = listOf(
          AtomLink("http://contoso/items").apply { rel = "alternate" })
      content = Content("text").apply { value = "Some text content" }
      summary = Summary().apply { value = "Some text." }
    })

    assertThat(syndicationFeed.items).isEqualTo(items)
  }
}