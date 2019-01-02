package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

/**
 *
 * @property domain a string that identifies a categorization taxonomy.
 */
@Root(name = "category", strict = false)
data class RssCategory(
  @get:Attribute(name = "domain", required = false)
  @param:Attribute(name = "domain", required = false)
  val domain: String? = null,

  @field:Text(required = false)
  @param:Text(required = false)
  val value: String? = null
)
