package eu.bbsapps.controller

import eu.bbsapps.data.MessageDataSource
import eu.bbsapps.data.model.Message
import io.ktor.http.cio.websocket.*
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.ConcurrentHashMap

class ChatController(
    private val messageDataSource: MessageDataSource
) {

    private val members = ConcurrentHashMap<String, Member>()

    fun onJoin(username: String, sessionId: String, socket: WebSocketSession) {
        if (members.contains(username)) {
            throw MemberAlreadyExistException()
        }

        members[username] = Member(username, sessionId, socket)
    }

    suspend fun sendMessage(senderUsername: String, message: String) {

        val messageEntity = Message(message, senderUsername, System.currentTimeMillis())

        val parsedMessage = Json.encodeToString(messageEntity)

        members.values.forEach { member ->
            member.socket.send(Frame.Text(parsedMessage))
        }

        messageDataSource.insertMessage(messageEntity)

    }

    suspend fun getAllMessages(): List<Message> {
        return messageDataSource.getAllMessages()
    }

    suspend fun disconnect(username: String){
        members[username]?.socket?.close()
        if (members.contains(username)){
            members.remove(username)
        }
    }



}
