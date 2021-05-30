package com.ouattararomuald.sample

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient

fun main() = runBlocking {
  val client = OkHttpClient.Builder().build()

  val syndicationReader = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    callFactory = CoroutineCallAdapterFactory(),
    httpClient = client
  )

  val reader = syndicationReader.create(RssReader::class.java)
  println(reader.read().await())

  val syndicationReader2 = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    httpClient = client
  )

  val reader2 = syndicationReader2.create(RssReader::class.java)
  println(reader2.readSync())

  fetchDataWithRxJava2(client)

  fetchDataWithRxJava3(client)

  fetchWithCustom(client)

  println()
}
