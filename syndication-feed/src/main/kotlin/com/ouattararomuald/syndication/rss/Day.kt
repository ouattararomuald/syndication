package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Text

data class Day(
  @field:Text()
  @param:Text()
  val value: String
)
