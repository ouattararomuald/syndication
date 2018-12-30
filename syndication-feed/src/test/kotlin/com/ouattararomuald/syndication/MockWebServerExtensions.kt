@file:JvmName("MockWebServerUtils")
package com.ouattararomuald.syndication

import okhttp3.mockwebserver.MockWebServer
import java.net.InetAddress

internal fun MockWebServer.runTests(tests: () -> Unit) {
  start(InetAddress.getLocalHost(), 2507)

  tests()

  shutdown()
}