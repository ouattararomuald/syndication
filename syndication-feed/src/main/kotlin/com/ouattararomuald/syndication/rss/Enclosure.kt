package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(strict = false)
data class Enclosure(
  @field:Attribute(name = "url")
  @param:Attribute(name = "url")
  val url: String,

  @field:Attribute(name = "length")
  @param:Attribute(name = "length")
  val length: Long,

  @field:Attribute(name = "type")
  @param:Attribute(name = "type")
  val type: String
) : Serializable
