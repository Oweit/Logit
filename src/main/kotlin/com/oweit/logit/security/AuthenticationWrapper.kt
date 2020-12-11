package com.oweit.logit.security

import com.oweit.logit.database.UserObject
import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationWrapper {
    companion object {
        fun getUser(): UserObject? {
            if (SecurityContextHolder.getContext().authentication != null) {
                val userName: String = SecurityContextHolder.getContext().authentication.principal as String
                return UserObject.getUserByUserName(userName)
            }
            return UserObject.getUserByUserName("AnonymousUser")
        }
    }
}