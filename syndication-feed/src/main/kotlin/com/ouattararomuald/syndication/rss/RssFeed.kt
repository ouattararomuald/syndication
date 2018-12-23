package com.ouattararomuald.syndication.rss

import com.ouattararomuald.syndication.Feed
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(name = "rss", strict = false)
internal class RssFeed(
  @field:Element(name = "channel")
  @param:Element(name = "channel")
  val channel: Channel
) : Feed {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RssFeed

    if (channel != other.channel) return false

    return true
  }

  override fun hashCode(): Int {
    return channel.hashCode()
  }

  override fun toString(): String {
    return "RssFeed(channel=$channel)"
  }
}
