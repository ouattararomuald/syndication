package com.ouattararomuald.syndication

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root
data class SkipHours(
  @param:Element(name = "hour")
  @field:Element(name = "hour")
  val hour: Int = 0
)
