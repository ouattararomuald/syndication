package com.ouattararomuald.syndication.atom

import com.ouattararomuald.syndication.Author
import com.ouattararomuald.syndication.Content
import com.ouattararomuald.syndication.Contributor
import com.ouattararomuald.syndication.Link
import com.ouattararomuald.syndication.Summary
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.util.ArrayList

/**
 * Represents a feed item, an Atom <entry>.
 *
 * @property id identifies the feed using a universally unique and permanent URI.
 * @property title contains a human readable title for the entry.
 * @property lastUpdatedTime the last time the feed was modified in a significant way.
 * @property published the time of the initial creation or first availability of the entry.
 * @property content contains or link to the complete content of the entry.
 * @property links list of related Web page.
 * @property summary conveys a short summary, abstract, or excerpt of the entry.
 * @property source contains metadata from the source feed if this entry is a copy.
 * @property authors authors of the entry.
 * @property categories categories that the entry belongs to.
 * @property contributors contributors of the entry.
 */
@Root(strict = false)
data class Entry(
  @field:Element(name = "id")
  @param:Element(name = "id")
  val id: String,

  @field:Element(name = "title")
  @param:Element(name = "title")
  val title: String,

  @field:Element(name = "updated")
  @param:Element(name = "updated")
  val lastUpdatedTime: String,

  @field:Element(name = "published", required = false)
  @param:Element(name = "published", required = false)
  val published: String? = null,

  @param:Element(name = "source", required = false)
  @field:Element(required = false)
  val source: AtomSource? = null,

  @param:Element(name = "content", required = false)
  @field:Element(name = "content", required = false)
  val content: Content? = null,

  @param:Element(name = "summary", required = false)
  @field:Element(name = "summary", required = false)
  val summary: Summary? = null,

  @field:ElementList(inline = true, required = false)
  @param:ElementList(inline = true, required = false)
  val links: List<Link>? = ArrayList(),

  @field:ElementList(inline = true, required = false)
  @param:ElementList(inline = true, required = false)
  val authors: List<Author>? = ArrayList(),

  @field:ElementList(inline = true, required = false)
  @param:ElementList(inline = true, required = false)
  val categories: List<AtomCategory>? = ArrayList(),

  @field:ElementList(inline = true, required = false)
  @param:ElementList(inline = true, required = false)
  val contributors: List<Contributor>? = ArrayList()
)
