package com.ouattararomuald.syndication

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

internal class SyndicationTest {

  @Test fun `exception on adapt with bad type`() {
    val syndication = Syndication("")
    val service = syndication.create(FeedReaderService::class.java)

    var throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.readBadType()
    }
    assertThat(throwable.message).isEqualTo(
        "expected type must be AtomFeed, RssFeed or generic of both types: Foo<AtomFeed>, Foo<RssFeed>")

    throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.readBadGenericType()
    }
    assertThat(throwable.message).isEqualTo(
        "returnType != RssFeed::class.java && returnType != AtomFeed::class.java")
  }

  @Test fun `custom return type with two annotations fails`() {
    val syndication = Syndication("")
    val service = syndication.create(FeedReaderService::class.java)

    val throwable = Assertions.assertThrows(IllegalStateException::class.java) {
      service.twoAnnotationsReadRss()
    }
    assertThat(throwable.message).isEqualTo(
        "method twoAnnotationsReadRss cannot be annotated with both @Atom and @Rss")
  }
}