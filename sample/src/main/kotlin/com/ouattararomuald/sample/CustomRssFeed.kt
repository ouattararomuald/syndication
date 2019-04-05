package com.ouattararomuald.sample

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(name = "rss", strict = false)
data class CustomRssFeed(
  @field:Element(name = "channel")
  @param:Element(name = "channel")
  val channel: Channel
) : Serializable