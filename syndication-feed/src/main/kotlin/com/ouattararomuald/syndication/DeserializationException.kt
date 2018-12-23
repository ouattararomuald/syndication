package com.ouattararomuald.syndication

import java.lang.Exception

/**
 * Called when the request could not be executed due to cancellation, a connectivity problem or
 * timeout. Because networks can fail during an exchange, it is possible that the remote server
 * accepted the request before the failure.
 */
class DeserializationException(message: String, cause: Throwable): Exception(message, cause)
