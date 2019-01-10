package com.ouattararomuald.sample

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import com.ouattararomuald.syndication.rss.RssFeed
import io.reactivex.observers.TestObserver
import io.reactivex.subscribers.TestSubscriber
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class RealFeedTest {

  companion object {
    internal const val FAKE_URL = "/file.mp4"
  }

  private lateinit var server: MockWebServer

  @BeforeEach fun setUp() {
    server = MockWebServer()
  }

  @AfterEach fun tearDown() {
    server.close()
  }

  @Test fun `atom feeds`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.ATOM_REDDIT)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.ATOM_SENAT)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)
      val syndication = Syndication(url = baseUrl.toString())
      val reader = syndication.create(RssReader::class.java)

      assertThat(reader.readAtom()).isNotNull()
      assertThat(reader.readAtom()).isNotNull()
    }
  }

  @Test fun `rss feeds`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_SENAT)
    )

    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_FRAGMENT_PODCAST)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)
      val syndication = Syndication(url = baseUrl.toString())
      val reader = syndication.create(RssReader::class.java)

      assertThat(reader.readRss()).isNotNull()
      assertThat(reader.readRss()).isNotNull()
    }
  }

  @Test fun `flowable rss feeds`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_SENAT)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)
      val syndication = Syndication(
          url = baseUrl.toString(),
          callFactory = RxJava2CallAdapterFactory.create()
      )
      val reader = syndication.create(RssReader::class.java)

      val subscriber = TestSubscriber<RssFeed>()
      reader.readFlowableRss().subscribe(subscriber)
      subscriber.assertComplete()
      subscriber.assertNoErrors()
      subscriber.assertValueCount(1)
      subscriber.assertValue { it.channel.title == "Sénat - derniers rapports" }
    }
  }

  @Test fun `maybe rss feeds`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_SENAT)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)
      val syndication = Syndication(
          url = baseUrl.toString(),
          callFactory = RxJava2CallAdapterFactory.create()
      )
      val reader = syndication.create(RssReader::class.java)

      val observer = TestObserver<RssFeed>()
      reader.readMaybeRss().subscribe(observer)
      observer.assertComplete()
      observer.assertNoErrors()
      observer.assertValueCount(1)
      observer.assertValue { it.channel.title == "Sénat - derniers rapports" }
    }
  }

  @Test fun `single rss feeds`() {
    server.enqueue(
        MockResponse()
            .setResponseCode(200)
            .setBody(Data.RSS_SENAT)
    )

    server.runTests {
      val baseUrl = server.url(FAKE_URL)
      val syndication = Syndication(
          url = baseUrl.toString(),
          callFactory = RxJava2CallAdapterFactory.create()
      )
      val reader = syndication.create(RssReader::class.java)

      val observer = TestObserver<RssFeed>()
      reader.readSingleRss().subscribe(observer)
      observer.assertComplete()
      observer.assertNoErrors()
      observer.assertValueCount(1)
      observer.assertValue { it.channel.title == "Sénat - derniers rapports" }
    }
  }
}