package com.oweit.logit.security

import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationWrapper {
companion object {
    fun getUser(): User {
        if (SecurityContextHolder.getContext().authentication != null) {
            val userName: String = SecurityContextHolder.getContext().authentication.principal as String
            return User(userName, "")
        }
        return User("AnonymousUser", "")
    }

}
}