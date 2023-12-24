package eu.bbsapps.data

import eu.bbsapps.data.model.Message
import org.litote.kmongo.coroutine.coroutine

class MessageDataSourceImpl : MessageDataSource {

    private val client = org.litote.kmongo.reactivestreams.KMongo.createClient().coroutine
    private val database = client.getDatabase("ChatDb")
    private val messages = database.getCollection<Message>()


    override suspend fun getAllMessages(): List<Message> {
        return messages.find().descendingSort(Message::timestamp).toList()

    }

    override suspend fun insertMessage(message: Message) {
        messages.insertOne(message)
    }
}