package com.oweit.logit.database

import org.springframework.context.annotation.Configuration
import com.mongodb.client.MongoDatabase

import com.mongodb.client.MongoClients

import com.mongodb.client.MongoClient

import com.mongodb.MongoClientSettings

import com.mongodb.ConnectionString
import org.springframework.context.annotation.Bean


@Configuration
class MongoConfig {

    @Bean
    fun getClient(): MongoClient {
        return MongoClients.create()
    }
}