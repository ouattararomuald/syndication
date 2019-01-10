package com.ouattararomuald.sample

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
  val syndicationReader = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      callFactory = CoroutineCallAdapterFactory()
  )

  val reader = syndicationReader.create(RssReader::class.java)
  println(reader.read().await())

  val syndicationReader2 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml"
  )

  val reader2 = syndicationReader2.create(RssReader::class.java)
  println(reader2.readSync())

  println("=====RxJava2 Adapter=====")

  val syndicationReader3 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      callFactory = RxJava2CallAdapterFactory()
  )

  val reader3 = syndicationReader3.create(RssReader::class.java)
  reader3.readFlowable().subscribe { rssFeed ->
    println(rssFeed)
  }

  reader3.readMaybe().subscribe { rssFeed ->
    println(rssFeed)
  }

  reader3.readObservable().subscribe { rssFeed ->
    println(rssFeed)
  }

  reader3.readSingle().subscribe { rssFeed ->
    println(rssFeed)
  }

  println()
}
