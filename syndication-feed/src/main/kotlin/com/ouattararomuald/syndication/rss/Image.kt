package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.Element

class Image(
  @field:Element(name = "url", required = false)
  @param:Element(name = "url", required = false)
  var url: String = "",

  @field:Element(name = "title", required = false)
  @param:Element(name = "title", required = false)
  var title: String = "",

  @field:Element(name = "link", required = false)
  @param:Element(name = "link", required = false)
  var link: String = ""
) {

  @field:Element(name = "width", required = false)
  var width: Int = 0

  @field:Element(name = "height", required = false)
  var height: Int = 0

  @field:Element(name = "description", required = false)
  var description: String = ""

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as Image

    if (url != other.url) return false
    if (title != other.title) return false
    if (link != other.link) return false
    if (width != other.width) return false
    if (height != other.height) return false
    if (description != other.description) return false

    return true
  }

  override fun hashCode(): Int {
    var result = url.hashCode()
    result = 31 * result + title.hashCode()
    result = 31 * result + link.hashCode()
    result = 31 * result + width
    result = 31 * result + height
    result = 31 * result + description.hashCode()
    return result
  }

  override fun toString(): String {
    return "Image(url='$url', title='$title', link='$link', width=$width, height=$height, description='$description')"
  }
}
