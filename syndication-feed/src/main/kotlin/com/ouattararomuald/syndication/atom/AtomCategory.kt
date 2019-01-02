package com.ouattararomuald.syndication.atom

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "category", strict = false)
data class AtomCategory(
  @get:Attribute(name = "term")
  @param:Attribute(name = "term")
  val term: String,

  @field:Attribute(name = "scheme", required = false)
  @param:Attribute(name = "scheme", required = false)
  val scheme: String? = null,

  @field:Attribute(name = "label", required = false)
  @param:Attribute(name = "label", required = false)
  val label: String? = null
)
