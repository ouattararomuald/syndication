package com.ouattararomuald.syndication.converters.simplexml

import com.ouattararomuald.syndication.Title
import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

internal class TitleConverter : Converter<Title> {
  override fun read(node: InputNode): Title {
    return Title(
        node.getAttribute("type")?.value,
        node.getAttribute("value")?.value
    )
  }

  override fun write(node: OutputNode, value: Title) {
    node.setAttribute("type", value.type)
    node.setAttribute("value", value.value)
  }
}