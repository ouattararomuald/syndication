package com.ouattararomuald.adapter.kotlin.coroutines

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

internal class CoroutineCallAdapterFactoryTest {

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
  fun `deserialize atom feed`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.ATOM_1_0)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val reader = Syndication(
          url = baseUrl.toString(),
          callFactory = CoroutineCallAdapterFactory()
      ).create(FeedReader::class.java)
      val syndicationFeed = runBlocking { reader.readAtom().await() }

      assertThat(syndicationFeed).isNotNull()
    }
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

      val reader = Syndication(
          url = baseUrl.toString(),
          callFactory = CoroutineCallAdapterFactory()
      ).create(FeedReader::class.java)
      val syndicationFeed = runBlocking { reader.readRss().await() }

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test fun `exception on adapt with bad type`() {
    val syndication =
        Syndication("http://www.example.ci", callFactory = CoroutineCallAdapterFactory())
    val service = syndication.create(FeedReader::class.java)

    var throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.readBadSimpleType()
    }
    assertThat(throwable.message).isEqualTo(
        "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>")

    throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.readBadGenericType()
    }
    assertThat(throwable.message).isEqualTo("returnType must be Deferred")

    throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.readUnparametizedDeferred()
    }
    assertThat(throwable.message).isEqualTo(
        "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>")

    throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.readParametizedDeferred()
    }
    assertThat(throwable.message).isEqualTo(
        "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>")
  }
}