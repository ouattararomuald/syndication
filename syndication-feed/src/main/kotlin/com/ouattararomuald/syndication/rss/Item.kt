package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.ArrayList

/**
 * Represents a feed item, an RSS <item>.
 *
 * @property link URL of the item.
 * @property title contains a human readable title for the item.
 * @property description description of the item.
 * @property published contains the time of the initial creation or first availability of the item.
 * @property guid a string that uniquely identifies the item.
 * @property comments URL of a page for comments relating to the item.
 * @property source contains metadata from the source feed if the item is a copy.
 * @property categories categories that the item belongs to.
 */
@Root(strict = false)
class Item {
  @field:Element(name = "link", required = false)
  var link: String = ""

  @field:Element(name = "title", required = false)
  var title: String = ""

  @field:Element(name = "description", required = false)
  var description: String = ""

  @field:Element(name = "pubDate", required = false)
  var published: String = ""

  @field:Element(name = "guid", required = false)
  var guid: String = ""

  @field:Element(name = "comments", required = false)
  var comments: String = ""

  @field:Element(name = "source", required = false)
  var source: Source? = null

  @field:ElementList(name = "category", inline = true, required = false)
  var categories: List<RssCategory> = ArrayList()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Item

    if (link != other.link) return false
    if (title != other.title) return false
    if (description != other.description) return false
    if (published != other.published) return false
    if (guid != other.guid) return false
    if (comments != other.comments) return false
    if (source != other.source) return false
    if (categories != other.categories) return false

    return true
  }

  override fun hashCode(): Int {
    var result = link.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + description.hashCode()
    result = 31 * result + published.hashCode()
    result = 31 * result + guid.hashCode()
    result = 31 * result + comments.hashCode()
    result = 31 * result + (source?.hashCode() ?: 0)
    result = 31 * result + categories.hashCode()
    return result
  }

  override fun toString(): String {
    return "Item(link='$link', title='$title', description='$description', published='$published', guid='$guid', comments='$comments', source=$source, categories=$categories)"
  }
}