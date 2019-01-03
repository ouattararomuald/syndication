package com.ouattararomuald.syndication.atom

import java.io.Serializable

data class AtomSource(
  val id: String? = null,
  val title: String? = null,
  val lastUpdatedTime: String? = null
) : Serializable
