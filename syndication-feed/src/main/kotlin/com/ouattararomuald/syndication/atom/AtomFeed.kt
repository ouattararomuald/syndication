package com.ouattararomuald.syndication.atom

import com.ouattararomuald.syndication.Author
import com.ouattararomuald.syndication.Content
import com.ouattararomuald.syndication.Contributor
import com.ouattararomuald.syndication.Copyright
import com.ouattararomuald.syndication.Link
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
 * @property lastUpdatedTime the last time the entry was modified.
 * @property language name of the language the feed is written in.
 * @property baseUri base uri of the syndication feed.
 * @property subtitle subtitle of the syndication feed.
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
data class AtomFeed(
  @param:Element(name = "id")
  @field:Element(name = "id")
  val id: String,

  @param:Element(name = "title")
  @field:Element(name = "title")
  val title: Title,

  @param:Element(name = "updated")
  @field:Element(name = "updated")
  val lastUpdatedTime: String,

  @param:Attribute(name = "lang", required = false)
  @param:Namespace(prefix = "xml")
  @field:Attribute(name = "lang", required = false)
  @field:Namespace(prefix = "xml")
  val language: String? = null,

  @param:Attribute(name = "base", required = false)
  @param:Namespace(prefix = "xml")
  @field:Attribute(name = "base", required = false)
  @field:Namespace(prefix = "xml")
  val baseUri: String? = null,

  @field:Element(name = "icon", required = false)
  @param:Element(name = "icon", required = false)
  val icon: String? = null,

  @field:Element(name = "logo", required = false)
  @param:Element(name = "logo", required = false)
  val logo: String? = null,

  @field:Element(name = "generator", required = false)
  @param:Element(name = "generator", required = false)
  val generator: Generator? = null,

  @field:Element(name = "subtitle", required = false)
  @param:Element(name = "subtitle", required = false)
  val subtitle: Content? = null,

  @field:Element(name = "rights", required = false)
  @param:Element(name = "rights", required = false)
  val copyright: Copyright? = null,

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
  val contributors: List<Contributor>? = ArrayList(),

  @field:ElementList(name = "entry", inline = true, required = false)
  @param:ElementList(name = "entry", inline = true, required = false)
  val items: List<Entry>? = ArrayList()
)
