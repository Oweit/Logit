package com.oweit.logit.database

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field

@Document
class UserObject  {


    @Field
    val userName: String? = null
}