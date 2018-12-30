package com.ouattararomuald.syndication.atom

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root

@Root(name = "category", strict = false)
class AtomCategory(
  @get:Attribute(name = "term", required = false)
  @param:Attribute(name = "term", required = false)
  val term: String,

  @field:Attribute(name = "scheme", required = false)
  @param:Attribute(name = "scheme", required = false)
  var scheme: String = "",

  @field:Attribute(name = "label", required = false)
  @param:Attribute(name = "label", required = false)
  var label: String = ""
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AtomCategory

    if (term != other.term) return false
    if (scheme != other.scheme) return false
    if (label != other.label) return false

    return true
  }

  override fun hashCode(): Int {
    var result = term.hashCode()
    result = 31 * result + scheme.hashCode()
    result = 31 * result + label.hashCode()
    return result
  }

  override fun toString(): String {
    return "AtomCategory(term='$term', scheme='$scheme', label='$label')"
  }
}
