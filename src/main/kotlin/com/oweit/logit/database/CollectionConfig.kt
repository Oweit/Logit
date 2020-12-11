package com.oweit.logit.database

import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import org.bson.Document
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CollectionConfig {

    @Autowired
    val database: MongoDatabase? = null

    @Bean
    fun getCollection(): MongoCollection<Document> {
        return database!!.getCollection("test")
    }
}