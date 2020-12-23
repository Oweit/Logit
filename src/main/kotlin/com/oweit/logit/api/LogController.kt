package com.oweit.logit.api

import org.springframework.http.MediaType.APPLICATION_JSON_VALUE
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.oweit.logit.security.AuthenticationWrapper
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired

@RestController
@RequestMapping("/log")
class LogController {

    @Autowired
    val database: MongoDatabase? = null

    @PostMapping(consumes = [APPLICATION_JSON_VALUE])
    fun log(@RequestBody json: String): String {
        val token: String = AuthenticationWrapper.getAPITokenId()
        try {
            val mapper = ObjectMapper().registerModule(KotlinModule())
            val map: Map<*, *> = mapper.readValue(json, Map::class.java)
            val insertMap: HashMap<String, Any> = HashMap()
            insertMap["tokenId"] = ObjectId(token)
            for ((key, value) in map) {
                if (value != null) {
                    insertMap[key as String] = value
                }
            }
            val dbDocument: Document = Document(insertMap)
            if (database != null) {
                val collection: MongoCollection<Document> = database!!.getCollection("logs")
                collection.insertOne(dbDocument)
            } else {
                return "Failed, could not connect to database"
            }

        } catch (e: Exception) {
            println(e)
        }

        return "ok"
    }

}
