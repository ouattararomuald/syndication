package com.ouattararomuald.syndication

import com.google.common.truth.Truth.assertThat
import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.http.HttpException
import com.ouattararomuald.syndication.rss.RssFeed
import okhttp3.Call
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock

internal class DefaultCallAdapterFactoryTest {

  companion object {
    internal const val FAKE_URL = "http://www.example.ci/file.mp4"
  }

  private val defaultCallAdapterFactory = DefaultCallAdapterFactory()

  @Test fun `exception on adapt with bad type`() {
    val setOfIntegers = setOf(1, 2)
    val throwable = assertThrows(IllegalStateException::class.java) {
      defaultCallAdapterFactory.get(setOfIntegers::class.java)
    }
    assertThat(throwable.message).isEqualTo(
        "returnType != RssFeed::class.java && returnType != AtomFeed::class.java")
  }

  @Suppress("UNCHECKED_CAST")
  @Test fun `adapt to rss feed`() {
    val callAdapter =
        defaultCallAdapterFactory.get(RssFeed::class.java) as CallAdapter<RssFeed, RssFeed>

    val mockedCall = createMockCall()
    val mockedResponse = createMockResponse(Data.RSS_2_0_SPEC)
    given(mockedCall.execute()).willReturn(mockedResponse)

    val rssFeed = callAdapter.adapt(mockedCall, RssFeed::class.java)
    assertThat(rssFeed).isNotNull()
    assertThat(rssFeed).isInstanceOf(RssFeed::class.java)
  }

  @Suppress("UNCHECKED_CAST")
  @Test fun `adapt to atom feed`() {
    val callAdapter =
        defaultCallAdapterFactory.get(AtomFeed::class.java) as CallAdapter<AtomFeed, AtomFeed>

    val mockedCall = createMockCall()
    val mockedResponse = createMockResponse(Data.ATOM_1_0)
    given(mockedCall.execute()).willReturn(mockedResponse)

    val atomFeed = callAdapter.adapt(mockedCall, AtomFeed::class.java)
    assertThat(atomFeed).isNotNull()
    assertThat(atomFeed).isInstanceOf(AtomFeed::class.java)
  }

  @Suppress("UNCHECKED_CAST")
  @Test fun `exception on failing http request`() {
    val callAdapter =
        defaultCallAdapterFactory.get(RssFeed::class.java) as CallAdapter<RssFeed, RssFeed>

    val mockedCall = createMockCall()
    val mockedResponse = createMockResponse(Data.RSS_2_0_SPEC, successful = false)
    given(mockedCall.execute()).willReturn(mockedResponse)

    val throwable = assertThrows(HttpException::class.java) {
      callAdapter.adapt(mockedCall, RssFeed::class.java)
    }
    assertThat(throwable.message).isEqualTo("Unsuccessful request")
  }

  @Suppress("UNCHECKED_CAST")
  @Test fun `deserialization failed`() {
    val callAdapter =
        defaultCallAdapterFactory.get(RssFeed::class.java) as CallAdapter<RssFeed, RssFeed>

    val mockedCall = createMockCall()
    val mockedResponse = createMockResponse(Data.UNDESERIALIZABLE_FEED)
    given(mockedCall.execute()).willReturn(mockedResponse)

    assertThrows(DeserializationException::class.java) {
      callAdapter.adapt(mockedCall, RssFeed::class.java)
    }
  }

  private fun createMockResponse(stringResponse: String, successful: Boolean = true): Response {
    return Response.Builder()
        .apply {
          body(ResponseBody.create(null, stringResponse))
          code(if (successful) 200 else 500)
          message("OK 200")
          protocol(Protocol.HTTP_1_1)
          request(Request.Builder().url(DefaultCallAdapterFactoryTest.FAKE_URL).build())
        }
        .build()
  }

  private fun createMockCall(): Call = mock(Call::class.java)
}