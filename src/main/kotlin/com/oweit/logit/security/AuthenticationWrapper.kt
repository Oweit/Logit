package com.oweit.logit.security

import com.oweit.logit.database.UserObject
import org.springframework.security.core.context.SecurityContextHolder

class AuthenticationWrapper {
    companion object {
        fun getUser(): UserObject? {
            if (SecurityContextHolder.getContext().authentication != null) {
                val userId: String = SecurityContextHolder.getContext().authentication.principal as String
                return UserObject.getUserByUserId(userId)
            }
            return UserObject.getUserByUserName("AnonymousUser")
        }
    }
}