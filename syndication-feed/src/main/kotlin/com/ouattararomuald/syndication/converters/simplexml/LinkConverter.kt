package com.ouattararomuald.syndication.converters.simplexml

import com.ouattararomuald.syndication.Link
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

internal class LinkConverter : Converter<Link> {
  override fun read(node: InputNode): Link {
    return Link(
        node.getAttribute("href")?.value,
        node.getAttribute("title")?.value,
        node.getAttribute("rel")?.value ?: "",
        node.getAttribute("type")?.value ?: "",
        node.getAttribute("hreflang")?.value ?: "",
        node.getAttribute("value")?.value ?: "",
        node.getAttribute("length")?.value?.toInt() ?: 0
    )
  }

  override fun write(node: OutputNode, value: Link) {
    node.setAttribute("href", value.href)
    node.setAttribute("title", value.title)
    node.setAttribute("rel", value.rel)
    node.setAttribute("type", value.type)
    node.setAttribute("hreflang", value.hreflang)
    node.setAttribute("value", value.value)
    node.setAttribute("length", value.length.toString())
  }
}