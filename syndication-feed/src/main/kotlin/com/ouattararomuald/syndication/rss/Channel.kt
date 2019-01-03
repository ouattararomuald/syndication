package com.ouattararomuald.syndication.rss

import com.ouattararomuald.syndication.Link
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable
import java.util.ArrayList

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
  val copyright: String? = null,

  @field:Element(name = "pubDate", required = false)
  @param:Element(name = "pubDate", required = false)
  val published: String? = null,

  @field:Element(name = "lastBuildDate", required = false)
  @param:Element(name = "lastBuildDate", required = false)
  val lastUpdatedTime: String? = null,

  @field:Element(name = "ttl", required = false)
  @param:Element(name = "ttl", required = false)
  val timeToLive: Int? = null,

  @field:Element(name = "generator", required = false)
  @param:Element(name = "generator", required = false)
  val generator: String? = null,

  @field:Element(name = "docs", required = false)
  @param:Element(name = "docs", required = false)
  val documentation: String? = null,

  @field:Element(name = "skipHours", required = false)
  @param:Element(name = "skipHours", required = false)
  val skipHours: SkipHours? = null,

  @field:Element(name = "skipDays", required = false)
  @param:Element(name = "skipDays", required = false)
  val skipDays: SkipDays? = null,

  @field:ElementList(name = "category", inline = true, required = false)
  @param:ElementList(name = "category", inline = true, required = false)
  val categories: List<RssCategory>? = ArrayList(),

  @field:ElementList(name = "image", inline = true, required = false)
  @param:ElementList(name = "image", inline = true, required = false)
  val images: List<Image>? = ArrayList(),

  @field:ElementList(name = "item", inline = true, required = false)
  @param:ElementList(name = "item", inline = true, required = false)
  val items: List<Item>? = ArrayList()
) : Serializable
