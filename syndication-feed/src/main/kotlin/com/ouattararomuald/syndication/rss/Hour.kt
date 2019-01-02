package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Text

data class Hour(
  @field:Text()
  @param:Text()
  val value: Int
)
