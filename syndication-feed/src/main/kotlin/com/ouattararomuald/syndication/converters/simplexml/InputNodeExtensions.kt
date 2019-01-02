@file:JvmName("InputNodeUtils")

package com.ouattararomuald.syndication.converters.simplexml

import org.simpleframework.xml.stream.InputNode

internal fun InputNode.readStringAttribute(name: String): String = getAttribute(name)?.value ?: ""

internal fun InputNode.readBooleanAttribute(name: String): Boolean = getAttribute(
    name)?.value?.toBoolean() ?: false

internal fun InputNode.readIntAttribute(name: String): Int = getAttribute(name)?.value?.toInt()
    ?: -1