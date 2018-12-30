@file:JvmName("MockWebServerUtils")

package com.ouattararomuald.adapter.kotlin.coroutines

import okhttp3.mockwebserver.MockWebServer
import java.net.InetAddress

fun MockWebServer.runTests(tests: () -> Unit) {
  start(InetAddress.getLocalHost(), 2507)

  tests()

  shutdown()
}