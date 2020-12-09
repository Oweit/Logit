package com.oweit.logit.database

import com.mongodb.client.MongoClient
import org.springframework.dao.DataAccessException

import com.mongodb.client.MongoDatabase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.ExecutableFindOperation
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.BasicQuery
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/db")
class DBTest {

    @Autowired
    val client: MongoClient? = null

    @Autowired
    val template: MongoTemplate? = null

    @GetMapping("/insert")
    fun test(): String {
        println(client)
        // client!!.startSession()

        println(template)
        template!!.save(UserObject())
        return "db"
    }

    @GetMapping("/")
    fun get(): String {
        val values: List<UserObject> = template!!.query(UserObject::class.java).all()

        return values.toString()
    }



}