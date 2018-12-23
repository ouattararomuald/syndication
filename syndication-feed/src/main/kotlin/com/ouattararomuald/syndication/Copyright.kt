package com.ouattararomuald.syndication

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Text

class Copyright(
  @field:Attribute(name = "type", required = false)
  @param:Attribute(name = "type", required = false)
  val type: String = "",

  @field:Text(required = false)
  @param:Text(required = false)
  var value: String = ""
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Copyright

    if (type != other.type) return false
    if (value != other.value) return false

    return true
  }

  override fun hashCode(): Int {
    var result = type.hashCode()
    result = 31 * result + value.hashCode()
    return result
  }

  override fun toString(): String {
    return "Copyright(type='$type', value='$value')"
  }
}
