package com.ouattararomuald.syndication.rss

import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root
import java.io.Serializable

/**
 * A hint for aggregators telling them which hours they can skip.
 *
 * @property hours contains up to 24 [Hour]s whose value is a number between 0 and 23,
 * representing a time in GMT, when aggregators, if they support the feature, may not read the
 * channel on hours listed in the `<skipHours>` element. The hour beginning at midnight is hour zero.
 */
@Root(strict = false)
data class SkipHours(
  @field:ElementList(name = "hour", inline = true)
  @param:ElementList(name = "hour", inline = true)
  val hours: List<Hour>
) : Serializable
