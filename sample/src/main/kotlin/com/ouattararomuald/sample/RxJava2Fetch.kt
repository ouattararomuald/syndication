package com.ouattararomuald.sample

import com.ouattararomuald.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ouattararomuald.syndication.Syndication
import okhttp3.OkHttpClient

@Suppress("DuplicatedCode") fun fetchDataWithRxJava2(client: OkHttpClient) {
  println("=====RxJava2 Adapter=====")

  val syndication = Syndication(
    url = "https://www.lequipe.fr/rss/actu_rss.xml",
    callFactory = RxJava2CallAdapterFactory(),
    httpClient = client
  )

  val reader = syndication.create(RssReader::class.java)
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
