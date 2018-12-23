package com.ouattararomuald.syndication

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root
data class SkipDays(
  @param:Element(name = "day")
  @field:Element(name = "day")
  val day: String = ""
)
