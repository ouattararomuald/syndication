package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text
import java.io.Serializable

/**
 *  Represents a category associated with an [Item] or feed ([RssFeed]).
 *
 * @property domain identifies a categorization taxonomy.
 * @property value a human-readable label for display in end-user applications.
 */
@Root(name = "category", strict = false)
data class RssCategory(
  @get:Attribute(name = "domain", required = false)
  @param:Attribute(name = "domain", required = false)
  val domain: String? = null,

  @field:Text(required = false)
  @param:Text(required = false)
  val value: String? = null
) : Serializable
