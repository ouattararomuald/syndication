package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(strict = false)
data class Image(
  @field:Attribute(name = "href", required = false)
  @param:Attribute(name = "href", required = false)
  val href: String? = null,

  @field:Element(name = "url", required = false)
  @param:Element(name = "url", required = false)
  val url: String? = null,

  @field:Element(name = "title", required = false)
  @param:Element(name = "title", required = false)
  val title: String? = null,

  @field:Element(name = "link", required = false)
  @param:Element(name = "link", required = false)
  val link: String? = null,

  @field:Element(name = "subtitle", required = false)
  @param:Element(name = "subtitle", required = false)
  val description: String? = null,

  @field:Element(name = "width", required = false)
  @param:Element(name = "width", required = false)
  val width: Int? = null,

  @field:Element(name = "height", required = false)
  @param:Element(name = "height", required = false)
  val height: Int? = null
) : Serializable
