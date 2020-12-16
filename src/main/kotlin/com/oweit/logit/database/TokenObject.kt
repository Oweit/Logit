package com.oweit.logit.database

import com.auth0.jwt.exceptions.TokenExpiredException
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.bson.types.ObjectId

class TokenObject {
    
    companion object {
        fun checkValid(token: String): String {
            val objectId: String = token
            val databaseMap: HashMap<String, Any?> = HashMap()
            databaseMap["tokenId"] = objectId
            val databaseDocument: Document = Document(databaseMap)
            val client: MongoDatabase = DatabaseConfig.getDatabase()
            val collection: MongoCollection<Document> = client.getCollection("apiTokens")
            if (collection.find(databaseDocument).count() == 1) {
                return token
            }
            throw TokenExpiredException("Token Expired")
        }
    }
}