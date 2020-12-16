package com.oweit.logit.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.mongodb.client.MongoDatabase
import com.oweit.logit.LoginDetails
import com.oweit.logit.database.UserObject
import org.bson.BsonValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.util.*
import org.bson.Document
import org.bson.types.ObjectId
import org.springframework.security.crypto.bcrypt.BCrypt

@RestController
@RequestMapping("/auth")
class RestController {

    @Autowired
    val algorithm: Algorithm? = null

    @Autowired
    val database: MongoDatabase? = null

    @GetMapping("/")
    fun getHome(): String {

        val user: UserObject? = AuthenticationWrapper.getUser()
        return "This is home"
    }

    @PostMapping("/token")
    fun login(@RequestBody loginDetails: LoginDetails): String {

        val userData: HashMap<String, Any> = HashMap()
        userData["username"] = loginDetails.username
        val userDataDocument: Document = Document(userData)
        val users: List<Document> = database!!.getCollection("user").find(userDataDocument).toList()
        return if (users.size == 1 ) { // successfully logged in
            try {
                val phash: String = users[0]["password"] as String
                if (!authenticate(phash, loginDetails.password)) throw Exception("Wrong password")
            } catch (e: Exception) {
                return "Wrong password"
            }
            val base: Date = Date()
            val baseLong: Long = base.time
            val end: Date = Date(baseLong + AuthenticationConstants.EXPIRENCY_LENGTH)
            val token: String = JWT.create().withClaim("userId", (users[0]["_id"] as ObjectId).toString()).withClaim("type", AuthenticationConstants.USER_TYPE).withIssuer("Logit").withExpiresAt(
                end
            ).sign(algorithm!!)
            token
        } else {
            "Wrong username"
        }
    }

    fun hash(password: String): String {
        return BCrypt.hashpw(password, BCrypt.gensalt())
    }

    fun authenticate(hash: String, password: String): Boolean {
        return BCrypt.checkpw(password, hash)
    }

}