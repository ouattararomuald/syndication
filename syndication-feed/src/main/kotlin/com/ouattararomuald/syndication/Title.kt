package com.ouattararomuald.syndication

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.Text

/**
 * Human-readable text, usually in small quantities
 *
 * - If `type="text"`, then this element contains plain text with no entity escaped html.
 *
 * ```xml
 * <title type="text">AT&amp;T bought by SBC!</title>
 * ```
 *
 * - If `type="html"`, then this element contains entity escaped html.
 *
 * ```xml
 * <title type="html">
 *   AT&amp;amp;T bought &amp;lt;b&amp;gt;by SBC&amp;lt;/b&amp;gt;!
 * </title>
 * ```
 *
 * - If `type="xhtml"`, then this element contains inline xhtml, wrapped in a div element.
 *
 * ```xml
 * <title type="xhtml">
 *   <div xmlns="http://www.w3.org/1999/xhtml">
 *     AT&amp;T bought <b>by SBC</b>!
 *   </div>
 * </title>
 * ```
 *
 * @property type determines how this information is encoded (default="text")
 */
@Element
class Title(
  @get:Attribute(name = "type", required = false)
  @param:Attribute(name = "type", required = false)
  val type: String = "",

  @field:Text(required = false)
  @param:Text(required = false)
  var value: String = ""
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Title

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
    return "Title(type='$type', value='$value')"
  }
}
