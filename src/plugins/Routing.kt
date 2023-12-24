package eu.bbsapps.plugins

import eu.bbsapps.chatController
import eu.bbsapps.routes.chatSocketRoute
import eu.bbsapps.routes.getAllMessages
import io.ktor.application.*
import io.ktor.routing.*

fun Application.configureRouting() {
    install(Routing) {
        chatSocketRoute(chatController = chatController)
        getAllMessages(chatController = chatController)
    }
}


