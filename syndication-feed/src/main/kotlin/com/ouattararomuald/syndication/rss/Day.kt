package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Text
import java.io.Serializable

data class Day(
  @field:Text()
  @param:Text()
  val value: String
) : Serializable
