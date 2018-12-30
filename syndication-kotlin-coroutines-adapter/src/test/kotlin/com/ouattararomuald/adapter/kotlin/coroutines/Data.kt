package com.ouattararomuald.adapter.kotlin.coroutines

internal class Data private constructor() {

  companion object {
    const val ATOM_1_0 = """<?xml version="1.0" encoding="utf-8"?>
      <feed xml:lang="en-us" xml:base="http://example.org/today/" CustomAttribute="Value" xmlns="http://www.w3.org/2005/Atom">
        <title type="text">Feed Title</title>
        <subtitle type="text">This is a sample feed</subtitle>
        <id>FeedID</id>

        <rights type="text">Copyright 2007</rights>
        <updated>2007-04-13T17:29:38Z</updated>
        <category term="FeedCategory" label="CategoryLabel" scheme="CategoryScheme" />
        <logo>http://contoso/image.jpg</logo>
        <author>
          <name>Jesper Aaberg</name>
          <uri>http://contoso/Aaberg</uri>
          <email>Jesper.Asberg@contoso.com</email>
        </author>
        <contributor>
          <name>Lene Aalling</name>
          <uri>http://contoso/Aalling</uri>
          <email>Lene.Aaling@contoso.com</email>
        </contributor>
        <generator>Sample Code</generator>
        <link rel="alternate" type="text/html" title="AtomLink Title" length="1000" href="http://contoso/link" />
        <link rel="self" type="application/atom+xml" href="http://example.org/feed.atom"/>

        <entry>
          <id>ItemID</id>
          <title type="text">Item Title</title>
          <updated>2007-04-13T17:29:38Z</updated>
          <link rel="alternate" href="http://contoso/items" />
          <content type="text">Some text content</content>
          <summary>Some text.</summary>
        </entry>

      </feed>
    """

    const val RSS_2_0_SPEC = """
      <rss version="2.0">
      <channel>
        <title>Liftoff News</title>
        <link>http://liftoff.msfc.nasa.gov/</link>
        <description>Liftoff to Space Exploration.</description>
        <language>en-us</language>
        <pubDate>Tue, 10 Jun 2003 04:00:00 GMT</pubDate>
        <lastBuildDate>Tue, 10 Jun 2003 09:41:01 GMT</lastBuildDate>
        <docs>http://blogs.law.harvard.edu/tech/rss</docs>
        <generator>Weblog Editor 2.0</generator>
        <managingEditor>editor@example.com</managingEditor>
        <webMaster>webmaster@example.com</webMaster>
          <item>
            <title>Star City</title>
            <link>http://liftoff.msfc.nasa.gov/news/2003/news-starcity.asp</link>
            <description>How do Americans get ready to work with Russians aboard the International Space Station? They take a crash course in culture, language and protocol at Russia's &lt;a href="http://howe.iki.rssi.ru/GCTC/gctc_e.htm">Star City&lt;/a>.</description>
            <pubDate>Tue, 03 Jun 2003 09:39:21 GMT</pubDate>
            <guid>http://liftoff.msfc.nasa.gov/2003/06/03.html#item573</guid>
          </item>
          <item>
            <description>Sky watchers in Europe, Asia, and parts of Alaska and Canada will experience a &lt;a href="http://science.nasa.gov/headlines/y2003/30may_solareclipse.htm">partial eclipse of the Sun&lt;/a> on Saturday, May 31st.</description>
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
          <link>http://feed/Alternate/AtomLink</link>
          <description>This is a sample feed</description>
          <language>en-us</language>

          <copyright>Copyright 2007</copyright>

          <managingEditor>Jesper.Aaberg@contoso.com</managingEditor>
          <lastBuildDate>Fri, 13 Apr 2007 17:29:38 Z</lastBuildDate>
          <category domain="CategoryScheme">FeedCategory</category>
          <a10:link rel="alternate" type="text/html" title="AtomLink Title" length="1000" href="http://contoso/link" />
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
            <link>http://feed/Alternate/AtomLink</link>
          </image>
          <a10:id>FeedID</a10:id>
          <a10:link customAttribute="value" rel="alternate" type="text/html" title="AtomLink Title" length="1000" href="http://contoso/link" />

          <CustomElement>Some text</CustomElement>
          <item>
            <guid isPermaLink="false">ItemID</guid>
            <link>http://contoso/items</link>
            <title>Item Title</title>
            <description>Some text content</description>
            <a10:updated>2007-04-13T17:29:38Z</a10:updated>
          </item>
        </channel>
      </rss>
    """
  }
}