package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable
import java.util.ArrayList

/**
 * Represents a feed item, an RSS <item>.
 *
 * @property link URL of the item.
 * @property title contains a human readable title for the item.
 * @property description subtitle of the item.
 * @property published contains the time of the initial creation or first availability of the item.
 * @property guid a string that uniquely identifies the item.
 * @property comments URL of a page for comments relating to the item.
 * @property source contains metadata from the source feed if the item is a copy.
 * @property categories categories that the item belongs to.
 */
@Root(strict = false)
data class Item(
  @field:Element(name = "link", required = false)
  @param:Element(name = "link", required = false)
  val link: String? = null,

  @field:Element(name = "title", required = false)
  @param:Element(name = "title", required = false)
  val title: String? = null,

  @field:Element(name = "description", required = false)
  @param:Element(name = "description", required = false)
  val description: String? = null,

  @field:Element(name = "pubDate", required = false)
  @param:Element(name = "pubDate", required = false)
  val published: String? = null,

  @field:Element(name = "guid", required = false)
  @param:Element(name = "guid", required = false)
  val guid: String? = null,

  @field:Element(name = "comments", required = false)
  @param:Element(name = "comments", required = false)
  val comments: String? = null,

  @field:Element(name = "source", required = false)
  @param:Element(name = "source", required = false)
  val source: RssSource? = null,

  @field:ElementList(name = "category", inline = true, required = false)
  @param:ElementList(name = "category", inline = true, required = false)
  val categories: List<RssCategory>? = ArrayList()
) : Serializable