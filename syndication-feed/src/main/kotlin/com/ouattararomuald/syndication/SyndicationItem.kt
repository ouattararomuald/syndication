package com.ouattararomuald.syndication

data class SyndicationItem(
  val id: String,
  val title: String,
  val lastUpdatedTime: String,
  val content: Content,
  val links: List<Link>,
  val summary: Summary,
  val published: String,
  val copyright: Copyright,
  val source: String,
  val authors:List<Author> = emptyList(),
  val categories: List<Category> = emptyList(),
  val contributors: List<Contributor> = emptyList()
)
