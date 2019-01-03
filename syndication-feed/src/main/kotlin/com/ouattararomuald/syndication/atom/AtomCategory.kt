package com.ouattararomuald.syndication.atom

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Root
import java.io.Serializable

/**
 * Represents a category associated with an [Entry] or feed ([AtomFeed]).
 *
 * @property term identifies the category to which the entry or feed belongs
 * @property scheme an IRI (Internationalized Resource Identifier) that identifies a categorization scheme.
 * @property label a human-readable label for display in end-user applications.
 */
@Root(name = "category", strict = false)
data class AtomCategory(
  @get:Attribute(name = "term")
  @param:Attribute(name = "term")
  val term: String,

  @field:Attribute(name = "scheme", required = false)
  @param:Attribute(name = "scheme", required = false)
  val scheme: String? = null,

  @field:Attribute(name = "label", required = false)
  @param:Attribute(name = "label", required = false)
  val label: String? = null
) : Serializable
