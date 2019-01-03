package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Text
import java.io.Serializable

data class Hour(
  @field:Text()
  @param:Text()
  val value: Int
) : Serializable
