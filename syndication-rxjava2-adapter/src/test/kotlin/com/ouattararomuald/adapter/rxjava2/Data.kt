package com.ouattararomuald.adapter.rxjava2

import com.ouattararomuald.syndication.rss.Channel
import com.ouattararomuald.syndication.rss.RssFeed

internal class Data private constructor() {

  companion object {
    const val ATOM_1_0 = """<?xml version="1.0" encoding="utf-8"?>
<feed xml:lang="en-us" xml:base="http://example.org/today/" xmlns="http://www.w3.org/2005/Atom">
  <title type="text">dive into mark</title>
  <subtitle type="html">A &lt;em&gt;lot&lt;/em&gt; of effort went into making this effortless</subtitle>
  <updated>2005-07-31T12:29:29Z</updated>
  <id>tag:example.org,2003:3</id>
  <link rel="alternate" type="text/html" hreflang="en" href="http://example.org/"/>
  <link rel="self" type="application/atom+xml" href="http://example.org/feed.atom"/>
  <rights>Copyright (c) 2003, Mark Pilgrim</rights>
  <generator uri="http://www.example.com/" version="1.0">Example Toolkit</generator>
  <logo>http://example.com/image.jpg</logo>
  <category term="FeedCategory" label="CategoryLabel" scheme="CategoryScheme" />
  <author>
    <name>Mark Pilgrim</name>
    <uri>http://example.org/</uri>
    <email>f8dy@example.com</email>
  </author>
  <contributor>
    <name>Sam Ruby</name>
  </contributor>
  <entry>
    <title>Atom draft-07 snapshot</title>
    <link rel="alternate" type="text/html" href="http://example.org/2005/04/02/atom"/>
    <link rel="enclosure" type="audio/mpeg" length="1337" href="http://example.org/audio/ph34r_my_podcast.mp3"/>
    <id>tag:example.org,2003:3.2397</id>
    <updated>2005-07-31T12:29:29Z</updated>
    <published>2003-12-13T08:29:29-04:00</published>
    <category term="FeedCategory" label="CategoryLabel" scheme="CategoryScheme" />
    <author>
      <name>Mark Pilgrim</name>
      <uri>http://example.org/</uri>
      <email>f8dy@example.com</email>
    </author>
    <contributor>
      <name>Sam Ruby</name>
    </contributor>
    <contributor>
      <name>Joe Gregorio</name>
    </contributor>
    <content type="xhtml">&lt;div xmlns="http://www.w3.org/1999/xhtml"&gt;&lt;p&gt;&lt;i&gt;[Update: The Atom draft is finished.]&lt;/i&gt;&lt;/p&gt;&lt;/div&gt;</content>
  </entry>
</feed>
    """

    const val RSS_2_0_SPEC = """
      <rss version="2.0" xmlns:atom="http://www.w3.org/2005/Atom">
      <channel>
        <title>Liftoff News</title>
        <atom:link>http://liftoff.msfc.nasa.gov/</atom:link>
        <link>http://liftoff.msfc.nasa.gov/2/</link>
        <description>Liftoff to Space Exploration.</description>
        <language>en-us</language>
        <pubDate>Tue, 10 Jun 2003 04:00:00 GMT</pubDate>
        <lastBuildDate>Tue, 10 Jun 2003 09:41:01 GMT</lastBuildDate>
        <docs>http://blogs.law.harvard.edu/tech/rss</docs>
        <generator>Weblog Editor 2.0</generator>
        <ttl>60</ttl>
        <category>Grateful Dead</category>
        <category domain="http://www.fool.com/cusips">MSFT</category>
        <skipDays>
          <day>Monday</day>
          <day>Tuesday</day>
        </skipDays>
        <skipHours>
          <hour>0</hour>
          <hour>1</hour>
        </skipHours>
        <image>
          <url>http://www.example.com/img.gif</url>
          <title>title</title>
          <link>https://www.example.fr</link>
          <width>119</width>
          <height>28</height>
        </image>
        <managingEditor>editor@example.com</managingEditor>
        <webMaster>webmaster@example.com</webMaster>
        <item>
          <title>Star City</title>
          <link>http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp</link>
          <description>How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href="http://howe.iki.rssi.ru/GCTC/gctc_e.htm"&gt;Star City&lt;/a&gt;.</description>
          <pubDate>Tue, 03 Jun 2003 09:39:21 GMT</pubDate>
          <guid>http://liftoff.msfc.nasa.gov/2003/06/03.html#item573</guid>
        </item>
        <item>
          <description>Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a &lt;a href="http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm"&gt;partial eclipse of the Sun&lt;/a&gt; on Saturday, May 31st.</description>
          <pubDate>Fri, 30 May 2003 11:06:42 GMT</pubDate>
          <guid>http://liftoff.msfc.nasa.gov/2003/05/30.html#item572</guid>
        </item>
      </channel>
      </rss>
    """

    const val RSS_2_0 = """
      <rss xmlns:a10="http://www.w3.org/2005/Atom" version="2.0">
        <channel CustomAttribute="Value">
          <title>Feed Title</title>
          <link>http://feed/Alternate/Link</link>
          <subtitle>This is a sample feed</subtitle>
          <language>en-us</language>

          <copyright>Copyright 2007</copyright>

          <managingEditor>Jesper.Aaberg@contoso.com</managingEditor>
          <lastBuildDate>Fri, 13 Apr 2007 17:29:38 Z</lastBuildDate>
          <category domain="CategoryScheme">FeedCategory</category>
          <a10:link rel="alternate" type="text/html" title="Link Title" length="1000" href="http://contoso/link" />
          <generator>Sample Code</generator>

          <a10:contributor>
            <a10:name>Lene Aalling</a10:name>
            <a10:uri>http://contoso/Aalling</a10:uri>
            <a10:email>Lene.Aalling@contoso.com</a10:email>
          </a10:contributor>

          <a10:author>
            <a10:name>Lene Aalling</a10:name>
            <a10:uri>http://contoso/Aalling</a10:uri>
            <a10:email>Lene.Aalling@contoso.com</a10:email>
          </a10:author>
          <image>
            <url>http://contoso/image.jpg</url>
            <title>Feed Title</title>
            <link>http://feed/Alternate/Link</link>
          </image>
          <a10:id>FeedID</a10:id>
          <a10:link customAttribute="value" rel="alternate" type="text/html" title="Link Title" length="1000" href="http://contoso/link" />

          <CustomElement>Some text</CustomElement>
          <item>
            <guid isPermaLink="false">ItemID</guid>
            <link>http://contoso/items</link>
            <title>Item Title</title>
            <subtitle>Some text content</subtitle>
            <a10:updated>2007-04-13T17:29:38Z</a10:updated>
          </item>
        </channel>
      </rss>
    """

    const val UNDESERIALIZABLE_FEED = """
      <rss xmlns:a10="http://www.w3.org/2005/Atom" version="2.0">
        <channel CustomAttribute="Value">
    """

    @JvmStatic
    fun newRssFeed(): RssFeed = RssFeed(Channel("Liftoff News", emptyList(), ""))
  }
}