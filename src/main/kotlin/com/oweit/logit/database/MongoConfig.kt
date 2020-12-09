package com.oweit.logit.database

import org.springframework.context.annotation.Configuration
import com.mongodb.client.MongoClients

import com.mongodb.client.MongoClient
import org.springframework.context.annotation.Bean


@Configuration
class MongoConfig {
    @Bean
    fun mongoClient(): MongoClient? {
        return MongoClients.create("mongodb://localhost:27017")
    }
}