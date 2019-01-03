package com.ouattararomuald.syndication.atom

import org.simpleframework.xml.Root
import java.io.Serializable

@Root(strict = false)
data class AtomSource(
  val id: String? = null,
  val title: String? = null,
  val lastUpdatedTime: String? = null
) : Serializable
