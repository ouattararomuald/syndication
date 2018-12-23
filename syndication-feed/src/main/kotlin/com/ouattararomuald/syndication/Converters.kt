@file:JvmName("ConvertersUtil")

package com.ouattararomuald.syndication

import com.ouattararomuald.syndication.atom.AtomCategory
import com.ouattararomuald.syndication.atom.AtomFeed
import com.ouattararomuald.syndication.atom.AtomItem
import com.ouattararomuald.syndication.atom.AtomLink
import com.ouattararomuald.syndication.rss.Item
import com.ouattararomuald.syndication.rss.RssCategory
import com.ouattararomuald.syndication.rss.RssFeed

internal fun AtomCategory.toCategory(): Category = Category(
    value = "term=$term, scheme=$scheme, label=$label"
)

internal fun AtomLink.toLink(): Link = Link(
    href = href
)

internal fun AtomItem.toSyndicationItem(): SyndicationItem = SyndicationItem(
    id = id,
    title = title,
    lastUpdatedTime = lastUpdatedTime,
    content = content,
    links = links.map { it.toLink() },
    summary = summary,
    published = published,
    copyright = copyright,
    source = source?.title ?: "",
    authors = authors,
    categories = categories.map { it.toCategory() },
    contributors = contributors
)

internal fun AtomFeed.toSyndicationFeed(): SyndicationFeed = SyndicationFeed(
    id = id,
    title = title.value,
    description = description.value,
    lastUpdatedTime = lastUpdatedTime,
    language = language,
    documentation = "",
    generator = generator,
    copyright = copyright,
    items = items.map { it.toSyndicationItem() },
    categories = categories.map { it.toCategory() },
    links = links.map { it.toLink() }
)

internal fun RssCategory.toCategory(): Category = Category(
    value = value
)

internal fun Item.toSyndicationItem(): SyndicationItem = SyndicationItem(
    id = guid,
    title = title,
    lastUpdatedTime = published,
    content = Content(type = "").apply { value = description },
    links = listOf(Link(link)),
    summary = Summary(type = "").apply { value = description },
    published = published,
    copyright = Copyright(type = ""),
    source = source?.value ?: "",
    authors = listOf(),
    categories = categories.map { it.toCategory() },
    contributors = listOf()
)

internal fun RssFeed.toSyndicationFeed(): SyndicationFeed = SyndicationFeed(
    id = "",
    title = channel.title,
    description = channel.description,
    lastUpdatedTime = channel.lastUpdatedTime,
    language = channel.language,
    documentation = channel.documentation,
    generator = channel.generator,
    copyright = Copyright(type = "").apply { value = channel.copyright },
    items = channel.items.map { it.toSyndicationItem() },
    categories = channel.categories.map { it.toCategory() },
    links = channel.links.map { it.toLink() },
    timeToLive = channel.timeToLive
)
