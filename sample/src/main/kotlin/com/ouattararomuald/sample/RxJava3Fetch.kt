package com.ouattararomuald.sample

import com.ouattararomuald.adapter.rxjava3.RxJava3CallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import okhttp3.OkHttpClient

@Suppress("DuplicatedCode") fun fetchDataWithRxJava3(client: OkHttpClient) {
  println("=====RxJava3 Adapter=====")

  val syndication = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    callFactory = RxJava3CallAdapterFactory(),
    httpClient = client
  )

  val reader = syndication.create(RssReaderRxJava3::class.java)
  reader.readFlowable().subscribe { rssFeed ->
    println(rssFeed)
  }

  reader.readMaybe().subscribe { rssFeed ->
    println(rssFeed)
  }

  reader.readObservable().subscribe { rssFeed ->
    println(rssFeed)
  }

  reader.readOnce().subscribe { rssFeed ->
    println(rssFeed)
  }
}
