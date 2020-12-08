package com.oweit.logit

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

class UserAuthentication:Authentication  {

    override fun getName(): String {
        TODO("Not yet implemented")
        return "UserAuthentication"
    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")

    }

    override fun getCredentials(): Any {
        TODO("Not yet implemented")
        return "Ok"

    }

    override fun getDetails(): Any {
        TODO("Not yet implemented")
        return "OkOk"
    }

    override fun getPrincipal(): Any {
        TODO("Not yet implemented")
        return "OOO"
    }

    override fun isAuthenticated(): Boolean {
        TODO("Not yet implemented")
        return false
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
        TODO("Not yet implemented")

    }
}
