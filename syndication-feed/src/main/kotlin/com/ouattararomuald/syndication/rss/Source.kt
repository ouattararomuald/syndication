package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Text

@Element
internal class Source(
  @get:Attribute(name = "url", required = false)
  @param:Attribute(name = "url", required = false)
  val url: String = "",

  @field:Text(required = false)
  @param:Text(required = false)
  var value: String = ""
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Source

    if (url != other.url) return false
    if (value != other.value) return false

    return true
  }

  override fun hashCode(): Int {
    var result = url.hashCode()
    result = 31 * result + value.hashCode()
    return result
  }

  override fun toString(): String {
    return "Title(url='$url', value='$value')"
  }
}
