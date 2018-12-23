package com.ouattararomuald.syndication

data class SyndicationFeed(
  val id: String,
  val title: String,
  val description: String,
  val lastUpdatedTime: String,
  val language: String,
  val documentation: String,
  val generator: String,
  val copyright: Copyright,
  val items: List<SyndicationItem> = emptyList(),
  val authors: List<Author> = emptyList(),
  val categories: List<Category> = emptyList(),
  val links: List<Link> = emptyList(),
  val timeToLive: Int = -1,
  val skipHours: SkipHours = SkipHours(hour = -1),
  val skipDays: SkipDays = SkipDays(day = "")
)
