package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Text

@Element(name = "category")
class RssCategory(
  @get:Attribute(name = "domain", required = false)
  @param:Attribute(name = "domain", required = false)
  val domain: String = "",

  @field:Text(required = false)
  @param:Text(required = false)
  var value: String = ""
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as RssCategory

    if (domain != other.domain) return false
    if (value != other.value) return false

    return true
  }

  override fun hashCode(): Int {
    var result = domain.hashCode()
    result = 31 * result + value.hashCode()
    return result
  }

  override fun toString(): String {
    return "RssCategory(domain='$domain', value='$value')"
  }
}
