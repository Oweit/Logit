package com.oweit.logit.database

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DatabaseConfig {

    @Autowired
    val mongoClient: MongoClient? = null

    @Bean
    fun getDatabase(): MongoDatabase {
        return mongoClient!!.getDatabase(databaseString)
    }

    companion object {

        val databaseString: String = "test"

        var staticDatabase: MongoDatabase? = null

        fun getDatabase(): MongoDatabase {
            if (staticDatabase == null) {
                staticDatabase = MongoClients.create().getDatabase(databaseString)
            }
            return staticDatabase as MongoDatabase
        }
    }

}