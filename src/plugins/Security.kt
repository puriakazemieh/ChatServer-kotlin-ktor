package eu.bbsapps.plugins

import eu.bbsapps.sessions.ChatSession
import io.ktor.application.*
import io.ktor.sessions.*

fun Application.configureSecurity() {
    install(Sessions) {
        cookie<ChatSession>("SESSION")
    }
    intercept(ApplicationCallPipeline.Features) {
        if (call.sessions.get<ChatSession>() == null) {
            val username = call.parameters["username"] ?: "Guest"
            call.sessions.set(ChatSession(username, generateSessionId()))
        }
    }
}
