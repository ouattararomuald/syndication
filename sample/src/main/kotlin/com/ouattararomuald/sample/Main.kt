package com.ouattararomuald.sample

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val syndicationReader = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      callFactory = CoroutineCallAdapterFactory()
  )

  val reader = syndicationReader.create(com.ouattararomuald.sample.RssReader::class.java)
  println(reader.read().await())

  val syndicationReader2 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml"
  )

  val reader2 = syndicationReader2.create(com.ouattararomuald.sample.RssReader::class.java)
  println(reader2.readSync())

  println("")
}
