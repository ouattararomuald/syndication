package com.ouattararomuald.syndication.atom

import com.ouattararomuald.syndication.Author
import com.ouattararomuald.syndication.Content
import com.ouattararomuald.syndication.Contributor
import com.ouattararomuald.syndication.Copyright
import com.ouattararomuald.syndication.Title
import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Namespace
import org.simpleframework.xml.Root
import java.util.ArrayList

/**
 * Represents an Atom 1.0 syndication feed.
 *
 * @property id ID of the feed.
 * @property title title of the feed.
 * @property lastUpdatedTime indicates the last time the entry was modified.
 * @property language name of the language the feed is written in.
 * @property baseUri base uri of the syndication feed.
 * @property description description of the syndication feed.
 * @property icon a small image which provides iconic visual identification for the feed.
 * @property logo a larger image which provides visual identification for the feed.
 * @property generator identifies the software used to generate the feed.
 * @property links links of the feed.
 * @property copyright copyright of the feed.
 * @property authors authors of the feed.
 * @property categories categories of the feed.
 * @property contributors contributors of the feed.
 * @property items entries of the feed.
 */
@Root(name = "feed", strict = false)
class AtomFeed(
  @param:Element(name = "id", required = false)
  @field:Element(name = "id", required = false)
  val id: String,

  @param:Element(name = "title", required = false)
  @field:Element(name = "title", required = false)
  val title: Title,

  @param:Element(name = "updated", required = false)
  @field:Element(name = "updated", required = false)
  val lastUpdatedTime: String
) {

  @field:Attribute(name = "lang", required = false)
  @field:Namespace(prefix = "xml")
  var language: String = ""

  @field:Attribute(name = "base", required = false)
  @field:Namespace(prefix = "xml")
  var baseUri: String = ""

  @field:Element(name = "subtitle", required = false)
  var description = Content()

  @field:Element(name = "icon", required = false)
  var icon = ""

  @field:Element(name = "logo", required = false)
  var logo = ""

  @field:Element(name = "generator", required = false)
  var generator = ""

  @field:ElementList(inline = true, required = false)
  var links: List<AtomLink> = ArrayList()

  @field:Element(name = "rights", required = false)
  var copyright = Copyright()

  @field:ElementList(inline = true, required = false)
  var authors: List<Author> = ArrayList()

  @field:ElementList(inline = true, required = false)
  var categories: List<AtomCategory> = ArrayList()

  @field:ElementList(inline = true, required = false)
  var contributors: List<Contributor> = ArrayList()

  @field:ElementList(inline = true, required = false)
  var items: List<AtomItem> = ArrayList()

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AtomFeed

    if (id != other.id) return false
    if (title != other.title) return false
    if (lastUpdatedTime != other.lastUpdatedTime) return false
    if (language != other.language) return false
    if (baseUri != other.baseUri) return false
    if (description != other.description) return false
    if (icon != other.icon) return false
    if (logo != other.logo) return false
    if (generator != other.generator) return false
    if (links != other.links) return false
    if (copyright != other.copyright) return false
    if (authors != other.authors) return false
    if (categories != other.categories) return false
    if (contributors != other.contributors) return false
    if (items != other.items) return false

    return true
  }

  override fun hashCode(): Int {
    var result = id.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + lastUpdatedTime.hashCode()
    result = 31 * result + language.hashCode()
    result = 31 * result + baseUri.hashCode()
    result = 31 * result + description.hashCode()
    result = 31 * result + icon.hashCode()
    result = 31 * result + logo.hashCode()
    result = 31 * result + generator.hashCode()
    result = 31 * result + links.hashCode()
    result = 31 * result + copyright.hashCode()
    result = 31 * result + authors.hashCode()
    result = 31 * result + categories.hashCode()
    result = 31 * result + contributors.hashCode()
    result = 31 * result + items.hashCode()
    return result
  }

  override fun toString(): String {
    return "AtomFeed(id='$id', title=$title, lastUpdatedTime='$lastUpdatedTime', language='$language', baseUri='$baseUri', description=$description, icon='$icon', logo='$logo', generator='$generator', link=$links, copyright=$copyright, authors=$authors, categories=$categories, contributors=$contributors, items=$items)"
  }
}