package com.ouattararomuald.syndication.converters.simplexml

import org.simpleframework.xml.convert.Converter
import org.simpleframework.xml.stream.InputNode
import org.simpleframework.xml.stream.OutputNode

internal class StringConverter : Converter<String> {
  override fun write(node: OutputNode, value: String) {
    node.value = value
  }

  override fun read(node: InputNode): String = node.value ?: ""
}