package eu.bbsapps.routes

import eu.bbsapps.controller.ChatController
import eu.bbsapps.controller.MemberAlreadyExistException
import eu.bbsapps.sessions.ChatSession
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.cio.websocket.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.sessions.*
import io.ktor.websocket.*
import kotlinx.coroutines.channels.consumeEach

fun Route.chatSocketRoute(chatController: ChatController) {
    webSocket("/chat-socket") {
        val sessions = call.sessions.get<ChatSession>()

        if (sessions == null) {
            close(CloseReason(CloseReason.Codes.VIOLATED_POLICY, "No Session"))
            return@webSocket
        }

        try {
            chatController.onJoin(username = sessions.username, sessionId = sessions.sessionId, socket = this)

            incoming.consumeEach { frame ->
                if (frame is Frame.Text) {
                    chatController.sendMessage(sessions.username, frame.readText())
                }

            }
        } catch (e: MemberAlreadyExistException) {
            call.respond(HttpStatusCode.Conflict)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            chatController.disconnect(sessions.username)
        }
    }
}


fun Route.getAllMessages(chatController: ChatController) {
    get("/messages") {
        call.respond(HttpStatusCode.OK, chatController.getAllMessages())
    }
}