package com.ouattararomuald.syndication.http

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.RssReader
import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.runTests
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.IOException

internal class HttpExtensionsTest {

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
  fun `failing http request`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(404)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)

      val syndicationFeed = runBlocking { RssReader(baseUrl.toString()).read(AtomFeed::class.java) }

      assertThat(syndicationFeed).isNull()
    }
  }

  @Test
  fun `http failure`() {
    server.runTests {

      val exception = assertThrows<IOException> {
        val baseUrl = server.url(FAKE_URL)

        val syndicationFeed = runBlocking {
          RssReader(baseUrl.toString()).read(AtomFeed::class.java)
        }

        assertThat(syndicationFeed).isNull()
      }

      assertThat(exception).isNotNull()
    }
  }
}