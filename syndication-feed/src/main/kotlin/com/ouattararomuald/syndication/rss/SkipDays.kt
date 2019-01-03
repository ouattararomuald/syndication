package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

/**
 * A hint for aggregators telling them which days they can skip.
 *
 * @property days contains up to seven [Day]s whose value is `Monday`, `Tuesday`, `Wednesday`,
 * `Thursday`, `Friday`, `Saturday` or `Sunday`. Aggregators may not read the channel during days
 * listed in the `<skipDays>` element.
 */
@Root(strict = false)
data class SkipDays(
  @field:ElementList(name = "day", inline = true)
  @param:ElementList(name = "day", inline = true)
  val days: List<Day>
) : Serializable
