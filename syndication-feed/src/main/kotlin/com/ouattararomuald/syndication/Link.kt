package com.ouattararomuald.syndication

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "link", strict = false)
data class Link(
  @param:Attribute(name = "href", required = false)
  @field:Attribute(name = "href", required = false)
  val href: String? = null,

  @param:Attribute(name = "title", required = false)
  @field:Attribute(name = "title", required = false)
  val title: String? = null,

  @param:Attribute(name = "rel", required = false)
  @field:Attribute(name = "rel", required = false)
  val rel: String? = null,

  @param:Attribute(name = "type", required = false)
  @field:Attribute(name = "type", required = false)
  val type: String? = null,

  @param:Attribute(name = "hreflang", required = false)
  @field:Attribute(name = "hreflang", required = false)
  val hreflang: String? = null,

  @param:Text(required = false)
  @field:Text(required = false)
  val value: String? = null,

  @param:Attribute(name = "length", required = false)
  @field:Attribute(name = "length", required = false)
  val length: Int? = null
)
