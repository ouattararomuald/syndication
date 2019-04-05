package com.ouattararomuald.sample

import com.ouattararomuald.syndication.Link
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

@Root(strict = false)
class Channel(
  @field:Element(name = "title")
  @param:Element(name = "title")
  val title: String,

  @field:ElementList(name = "link", inline = true)
  @param:ElementList(name = "link", inline = true)
  val links: List<Link>,

  @field:Element(name = "description")
  @param:Element(name = "description")
  val description: String,

  @field:Element(name = "language", required = false)
  @param:Element(name = "language", required = false)
  val language: String? = null,

  @field:Element(name = "copyright", required = false)
  @param:Element(name = "copyright", required = false)
  val copyright: String? = null
) : Serializable
