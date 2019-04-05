package com.ouattararomuald.sample

import com.ouattararomuald.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.ouattararomuald.adapter.rxjava2.RxJava2CallAdapterFactory
import com.ouattararomuald.sample.TrustAllX509TrustManager.allowAllHostNames
import com.ouattararomuald.syndication.Syndication
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import java.security.KeyManagementException
import java.security.NoSuchAlgorithmException
import javax.net.ssl.KeyManager
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager

fun sslContext(keyManagers: Array<KeyManager>?, trustManagers: Array<TrustManager>): SSLContext {
  try {
    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(keyManagers,
        trustManagers,
        null)
    return sslContext
  } catch (e: NoSuchAlgorithmException) {
    throw IllegalStateException("Couldn't init TLS context", e)
  } catch (e: KeyManagementException) {
    throw IllegalStateException("Couldn't init TLS context", e)
  }

}

fun main() = runBlocking {
  val trustManager = TrustAllX509TrustManager.INSTANCE
  val client = OkHttpClient.Builder()
      .sslSocketFactory(
          sslContext(null,
              arrayOf<TrustManager>(trustManager)).socketFactory,
          trustManager)
      .hostnameVerifier(allowAllHostNames())
      .build()

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

  println("=====RxJava2 Adapter=====")

  val syndicationReader3 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      callFactory = RxJava2CallAdapterFactory(),
      httpClient = client
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

  reader3.readOnce().subscribe { rssFeed ->
    println(rssFeed)
  }

  println("=====Custom Parser=====")

  val syndicationReader4 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      httpClient = client
  )

  val reader4 = syndicationReader4.create(CustomReader::class.java)
  println(reader4.read())

  val syndicationReader5 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      callFactory = CoroutineCallAdapterFactory(),
      httpClient = client
  )

  val reader5 = syndicationReader5.create(CustomReader::class.java)
  println(reader5.readAsync().await())

  val syndicationReader6 = Syndication(
      url = "https://www.lequipe.fr/rss/actu_rss.xml",
      callFactory = RxJava2CallAdapterFactory(),
      httpClient = client
  )

  val reader6 = syndicationReader6.create(CustomReader::class.java)

  reader6.readOnce().subscribe { rssFeed ->
    println(rssFeed)
  }

  println()
}
