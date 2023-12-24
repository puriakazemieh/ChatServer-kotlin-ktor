package eu.bbsapps

import eu.bbsapps.controller.ChatController
import eu.bbsapps.data.MessageDataSourceImpl
import eu.bbsapps.plugins.*
import io.ktor.application.*
import io.ktor.response.*
import io.ktor.request.*

val chatController=ChatController(MessageDataSourceImpl())

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
fun Application.module(testing: Boolean = false) {
    configureSockets()
    configureRouting()
    configureSerialization()
    configureMonitoring()
    configureSecurity()
}

