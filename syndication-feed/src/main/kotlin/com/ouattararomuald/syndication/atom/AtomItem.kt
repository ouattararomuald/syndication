package com.ouattararomuald.syndication.atom

import com.ouattararomuald.syndication.Author
import com.ouattararomuald.syndication.Content
import com.ouattararomuald.syndication.Contributor
import com.ouattararomuald.syndication.Copyright
import com.ouattararomuald.syndication.Source
import com.ouattararomuald.syndication.Summary
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.ArrayList

/**
 * Represents a feed item, for example an RSS <item> or an Atom <entry>.
 *
 * @property id identifies the feed using a universally unique and permanent URI.
 * @property title contains a human readable title for the entry.
 * @property lastUpdatedTime indicates the last time the feed was modified in a significant way.
 * @property content contains or link to the complete content of the entry.
 * @property links identifies a related Web page.
 * @property summary Conveys a short summary, abstract, or excerpt of the entry.
 * Summary should be provided if there either is no content provided for the entry,
 * or that content is not inline (i.e., contains a src attribute), or if the content
 * is encoded in base64. More info.
 * @property copyright conveys information about copyright,
 * e.g. copyrights, held in and over the feed.
 * @property published contains the time of the initial creation or first availability of the entry.
 * @property source contains metadata from the source feed if this entry is a copy.
 * @property authors authors of the entry.
 * @property categories categories that the entry belongs to.
 * @property contributors contributors of the entry.
 */
@Root(name = "entry", strict = false)
internal class AtomItem(
  @field:Element(name = "id", required = false)
  @param:Element(name = "id", required = false)
  val id: String,

  @field:Element(name = "title", required = false)
  @param:Element(name = "title", required = false)
  val title: String,

  @field:Element(name = "updated", required = false)
  @param:Element(name = "updated", required = false)
  val lastUpdatedTime: String
) {

  @field:Element(name = "content", required = false)
  var content: Content = Content()

  @field:ElementList(inline = true, required = false)
  var links: List<AtomLink> = ArrayList()

  @field:Element(name = "summary", required = false)
  var summary: Summary = Summary()

  @field:Element(name = "published", required = false)
  var published: String = ""

  @field:Element(name = "rights", required = false)
  var copyright: Copyright = Copyright()

  @field:Element(name = "source", required = false)
  var source: Source? = null

  @field:ElementList(inline = true, required = false)
  var authors: MutableList<Author> = mutableListOf()

  @field:ElementList(name = "category", inline = true, required = false)
  var categories: MutableList<AtomCategory> = mutableListOf()

  @field:ElementList(inline = true, required = false)
  var contributors: MutableList<Contributor> = mutableListOf()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AtomItem

    if (id != other.id) return false
    if (title != other.title) return false
    if (lastUpdatedTime != other.lastUpdatedTime) return false
    if (content != other.content) return false
    if (links != other.links) return false
    if (summary != other.summary) return false
    if (published != other.published) return false
    if (copyright != other.copyright) return false
    if (source != other.source) return false
    if (authors != other.authors) return false
    if (categories != other.categories) return false
    if (contributors != other.contributors) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + lastUpdatedTime.hashCode()
    result = 31 * result + content.hashCode()
    result = 31 * result + links.hashCode()
    result = 31 * result + summary.hashCode()
    result = 31 * result + published.hashCode()
    result = 31 * result + copyright.hashCode()
    result = 31 * result + (source?.hashCode() ?: 0)
    result = 31 * result + authors.hashCode()
    result = 31 * result + categories.hashCode()
    result = 31 * result + contributors.hashCode()
    return result
  }

  override fun toString(): String {
    return "AtomItem(id='$id', title='$title', lastUpdatedTime='$lastUpdatedTime', content=$content, link=$links, summary=$summary, published='$published', copyright=$copyright, source=$source, authors=$authors, categories=$categories, contributors=$contributors)"
  }
}