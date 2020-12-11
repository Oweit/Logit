package com.oweit.logit.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired



class UserObject (val id: String, val userName: String) {

    companion object {
        fun getUserByUserName(userName: String): UserObject? {
            val database: MongoDatabase = DatabaseConfig.getDatabase()
            val userCollection: MongoCollection<Document> = database!!.getCollection("user")
            val userFilter: HashMap<String, Any> = HashMap()
            userFilter["username"] = userName
            val userFilterDocument: Document = Document(userFilter)
            val result: List<Document> = userCollection.find(userFilterDocument).toList()
            if (result.size == 1) {
                return UserObject(result[0]["_id"].toString(), result[0].getString("username"))
            }
            return null
        }
    }

    override fun toString(): String {
        return "User: $id"
    }

}