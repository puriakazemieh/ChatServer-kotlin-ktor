package eu.bbsapps.data

import eu.bbsapps.data.model.Message

interface MessageDataSource {
    suspend fun getAllMessages():List<Message>

    suspend fun insertMessage(message: Message)

}