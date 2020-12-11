package com.oweit.logit.api

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.FindIterable
import com.mongodb.client.MongoCollection
import com.mongodb.client.MongoDatabase
import com.oweit.logit.api.requests.NewTokenRequest
import com.oweit.logit.api.responses.ApiTokensResponse
import com.oweit.logit.database.UserObject
import com.oweit.logit.security.AuthenticationConstants
import com.oweit.logit.security.AuthenticationWrapper
import com.oweit.logit.security.User
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid
import kotlin.collections.HashMap

@RestController
@RequestMapping("/apitoken")
class AccessTokenControl {

    @Autowired
    val database: MongoDatabase? = null

    @Autowired
    val algorithm: Algorithm? = null

    @GetMapping("/")
    fun getTokens(): List<ApiTokensResponse> {
        val user: UserObject? = AuthenticationWrapper.getUser()
        if (user == null) {
            return arrayListOf<ApiTokensResponse>()
        }

        val collection: MongoCollection<Document> = database!!.getCollection("apiTokens")
        val result = collection.find().toList()
        val returnResult = arrayListOf<ApiTokensResponse>()
        for (r  in result) {
            returnResult.add(ApiTokensResponse(r))
        }
        return returnResult
    }

    @PostMapping("/")
    fun newToken(@RequestBody body: @Valid NewTokenRequest): String {
        val user: UserObject = AuthenticationWrapper.getUser() ?: return "Error"
        return if (database != null) {
            val tokenId: String = UUID.randomUUID().toString()
            val collection: MongoCollection<Document> = database!!.getCollection("apiTokens")
            val documentValues: HashMap<String, Any> = HashMap()
            documentValues["name"] = body.name
            documentValues["user"] = ObjectId(user.id)
            val jwt: String = JWT.create().withIssuer("Logit").withClaim("type", AuthenticationConstants.PROGRAMMABLE_TOKEN_TYPE).withClaim("tokenId", tokenId).sign(algorithm!!)
            documentValues["tokenId"] = tokenId
            documentValues["token"] = jwt
            val document: Document = Document(documentValues)
            collection.insertOne(document)
            return jwt
        } else {
            "Error"
        }
    }
}