package com.ouattararomuald.adapter.kotlin.coroutines

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
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
  fun `Atom_1_0 deserializing feed should success`() {
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
      ).create(AtomFeedReader::class.java)
      val syndicationFeed = runBlocking { reader.read().await() }

      assertThat(syndicationFeed).isNotNull()
    }
  }

  @Test
  fun `Rss_1_0 deserializing feed should success`() {
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
      ).create(RssFeedReader::class.java)
      val syndicationFeed = runBlocking { reader.read().await() }

      assertThat(syndicationFeed).isNotNull()
    }
  }
}