package com.oweit.logit.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/db")
class DBTest {

    @Autowired
    val collection: MongoCollection<Document>? = null

    @GetMapping("/insert")
    fun test(): String {
        val value: HashMap<String, Any> = HashMap()
        value.put("name", "Stefan")
        val doc: Document = Document(value)
        collection!!.insertOne(doc)
        return "db"
    }

    @GetMapping("/")
    fun get(): String {
        val client: MongoClient = MongoClients.create()
        return collection!!.find().toList().toString()
    }
}