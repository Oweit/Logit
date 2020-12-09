package com.oweit.logit.security

import com.auth0.jwt.algorithms.Algorithm
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier


@Configuration
class JWTWrapper {
    @Bean
    fun getAlgorithm(): Algorithm {
        return Algorithm.HMAC256(AuthenticationConstants.SECRET)
    }

    @Bean
    fun verifier(): JWTVerifier {
        val algorithm = Algorithm.HMAC256(AuthenticationConstants.SECRET)
        val verifier: JWTVerifier = JWT.require(algorithm)
            .withIssuer(AuthenticationConstants.ISSUER)
            .build() //Reusable verifier instance
        return verifier
    }
}