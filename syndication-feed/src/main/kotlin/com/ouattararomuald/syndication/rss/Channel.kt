package com.ouattararomuald.syndication.rss

import com.ouattararomuald.syndication.atom.AtomLink
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.ArrayList

@Root(strict = false)
internal class Channel(
  @param:Element(name = "title", required = false)
  @field:Element(name = "title", required = false)
  val title: String = "",

  @param:Element(name = "description", required = false)
  @field:Element(name = "description", required = false)
  val description: String = ""
) {

  @field:ElementList(inline = true, required = false)
  var links: List<AtomLink> = ArrayList()

  @field:Element(name = "language", required = false)
  var language: String = ""

  @field:Element(name = "copyright", required = false)
  var copyright: String = ""

  @field:Element(name = "pubDate", required = false)
  var published: String = ""

  @field:Element(name = "lastBuildDate", required = false)
  var lastUpdatedTime: String = ""

  @field:Element(name = "ttl", required = false)
  var timeToLive: Int = -1

  @field:ElementList(name = "category", inline = true, required = false)
  var categories: List<RssCategory> = ArrayList()

  @field:Element(name = "generator", required = false)
  var generator = ""

  @field:Element(name = "docs", required = false)
  var documentation: String = ""

  @field:ElementList(name = "image", inline = true, required = false)
  var images: List<Image> = ArrayList()

  @field:ElementList(name = "item", inline = true, required = false)
  var items: List<Item> = ArrayList()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Channel

    if (title != other.title) return false
    if (description != other.description) return false
    if (links != other.links) return false
    if (language != other.language) return false
    if (copyright != other.copyright) return false
    if (published != other.published) return false
    if (lastUpdatedTime != other.lastUpdatedTime) return false
    if (timeToLive != other.timeToLive) return false
    if (categories != other.categories) return false
    if (generator != other.generator) return false
    if (documentation != other.documentation) return false
    if (images != other.images) return false
    if (items != other.items) return false

    return true
  }

  override fun hashCode(): Int {
    var result = title.hashCode()
    result = 31 * result + description.hashCode()
    result = 31 * result + links.hashCode()
    result = 31 * result + language.hashCode()
    result = 31 * result + copyright.hashCode()
    result = 31 * result + published.hashCode()
    result = 31 * result + lastUpdatedTime.hashCode()
    result = 31 * result + timeToLive
    result = 31 * result + categories.hashCode()
    result = 31 * result + generator.hashCode()
    result = 31 * result + documentation.hashCode()
    result = 31 * result + images.hashCode()
    result = 31 * result + items.hashCode()
    return result
  }

  override fun toString(): String {
    return "Channel(title='$title', description='$description', links=$links, language='$language', copyright='$copyright', published='$published', lastUpdatedTime='$lastUpdatedTime', timeToLive=$timeToLive, categories=$categories, generator='$generator', documentation='$documentation', images=$images, items=$items)"
  }
}
