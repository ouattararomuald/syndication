package com.ouattararomuald.syndication

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

/**
 * Author of the feed.
 *
 * @param name name of the author.
 * @property uri home page of the author.
 * @property uri email address of the author.
 */
@Root(strict = false)
data class Author(
  @field:Element(name = "name")
  @param:Element(name = "name")
  val name: String,

  @field:Element(name = "uri", required = false)
  @param:Element(name = "uri", required = false)
  val uri: String? = null,

  @field:Element(name = "email", required = false)
  @param:Element(name = "email", required = false)
  val email: String? = null
)
