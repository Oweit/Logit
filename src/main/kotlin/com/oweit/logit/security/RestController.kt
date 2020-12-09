package com.oweit.logit.security

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.oweit.logit.LoginDetails
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/auth")
class RestController {

    @Autowired
    val algorithm: Algorithm? = null

    @GetMapping("/")
    fun getHome(): String {
        val user: User = AuthenticationWrapper.getUser()
        println(user.username)
        return "This is home"
    }

    @GetMapping("/token")
    fun login(@RequestBody loginDetails: LoginDetails): String {
        val base: Date = Date()
        val baseLong: Long = base.time
        val end: Date = Date(baseLong + AuthenticationConstants.EXPIRENCY_LENGTH)
        val token: String = JWT.create().withClaim("username", loginDetails.username).withIssuer("Logit").withExpiresAt(
            end
        ).sign(algorithm!!)
        return token
    }
}