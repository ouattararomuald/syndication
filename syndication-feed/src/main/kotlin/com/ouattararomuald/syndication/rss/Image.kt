package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Element
import java.io.Serializable

data class Image(
  @field:Element(name = "url", required = false)
  @param:Element(name = "url", required = false)
  val url: String,

  @field:Element(name = "title", required = false)
  @param:Element(name = "title", required = false)
  val title: String,

  @field:Element(name = "link", required = false)
  @param:Element(name = "link", required = false)
  val link: String,

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
