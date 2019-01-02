package com.ouattararomuald.syndication.atom

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(strict = false)
data class Generator(
  @field:Text
  @param:Text
  val value: String,

  @field:Attribute(name = "uri", required = false)
  @param:Attribute(name = "uri", required = false)
  val uri: String? = null,

  @field:Attribute(name = "version", required = false)
  @param:Attribute(name = "version", required = false)
  val version: String? = null
)
