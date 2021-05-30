package com.ouattararomuald.sample

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

@Suppress("DuplicatedCode") suspend fun fetchWithCustom(client: OkHttpClient) {
  println("=====Custom Parser=====")

  val syndication = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    httpClient = client
  )
  val reader = syndication.create(CustomReader::class.java)
  println(reader.read())

  val syndication2 = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    callFactory = CoroutineCallAdapterFactory(),
    httpClient = client
  )
  val reader2 = syndication2.create(CustomReader::class.java)
  println(reader2.readAsync().await())

  val syndication3 = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    callFactory = RxJava2CallAdapterFactory(),
    httpClient = client
  )
  val reader3 = syndication3.create(CustomReader::class.java)
  reader3.readOnce().subscribe { rssFeed ->
    println(rssFeed)
  }
}
