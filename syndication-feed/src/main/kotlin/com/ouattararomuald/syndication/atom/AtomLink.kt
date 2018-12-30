package com.ouattararomuald.syndication.atom

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import org.simpleframework.xml.Text

@Root(name = "link", strict = false)
class AtomLink(
  @field:Attribute(name = "href", required = false)
  @param:Attribute(name = "href", required = false)
  val href: String = ""
) {

  @field:Attribute(name = "title", required = false)
  var title: String = ""

  @field:Attribute(name = "rel", required = false)
  var rel: String = ""

  @field:Attribute(name = "type", required = false)
  var type: String = ""

  @field:Attribute(name = "hreflang", required = false)
  var hreflang: String = ""

  @field:Text(required = false)
  var value: String = ""

  @field:Attribute(name = "length", required = false)
  var length: Int = 0

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as AtomLink

    if (href != other.href) return false
    if (title != other.title) return false
    if (rel != other.rel) return false
    if (type != other.type) return false
    if (hreflang != other.hreflang) return false
    if (value != other.value) return false
    if (length != other.length) return false

    return true
  }

  override fun hashCode(): Int {
    var result = href.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + rel.hashCode()
    result = 31 * result + type.hashCode()
    result = 31 * result + hreflang.hashCode()
    result = 31 * result + value.hashCode()
    result = 31 * result + length
    return result
  }

  override fun toString(): String {
    return "AtomLink(href='$href', title='$title', rel='$rel', type='$type', hreflang='$hreflang', value='$value', length=$length)"
  }
}
