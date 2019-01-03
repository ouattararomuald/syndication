package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Text
import java.io.Serializable

@Element
data class RssSource(
  @get:Attribute(name = "url", required = false)
  @param:Attribute(name = "url", required = false)
  val url: String? = null,

  @field:Text(required = false)
  @param:Text(required = false)
  val value: String? = null
) : Serializable
