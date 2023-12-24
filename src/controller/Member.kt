package eu.bbsapps.controller

import io.ktor.http.cio.websocket.*

data class Member(
    val username: String,
    val sessionId: String,
    val socket: WebSocketSession
)
