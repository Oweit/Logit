package com.oweit.logit.security

class AuthenticationConstants {
    companion object {
        val HEADER_STRING: String = "Authorization"
        val SECRET: String = "secret"
        val ISSUER: String = "Logit"
        val EXPIRENCY_LENGTH: Long = 2592000000 // one month
        val PROGRAMMABLE_TOKEN_TYPE: String = "API_TOKEN"
        val USER_TYPE: String = "USER_TOKEN"
        val USER_TYPE_BEARER: String = "Bearer"
        val PROGRAMMABLE_TOKEN_TYPE_BEARER: String = "APIKEY"
    }
}