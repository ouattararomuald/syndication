package com.ouattararomuald.syndication

import org.simpleframework.xml.Element
import org.simpleframework.xml.Root

@Root(strict = false)
class Contributor(
  @field:Element(name = "name", required = false)
  @param:Element(name = "name", required = false)
  val name: String = "",

  @field:Element(name = "uri", required = false)
  @param:Element(name = "uri", required = false)
  var uri: String = "",

  @field:Element(name = "email", required = false)
  @param:Element(name = "email", required = false)
  var email: String = ""
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Contributor

    if (name != other.name) return false
    if (uri != other.uri) return false
    if (email != other.email) return false

    return true
  }

  override fun hashCode(): Int {
    var result = name.hashCode()
    result = 31 * result + uri.hashCode()
    result = 31 * result + email.hashCode()
    return result
  }

  override fun toString(): String {
    return "Contributor(name='$name', uri='$uri', email='$email')"
  }
}
