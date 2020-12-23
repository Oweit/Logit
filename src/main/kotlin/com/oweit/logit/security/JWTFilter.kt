package com.oweit.logit.security



import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.oweit.logit.database.TokenObject
import com.oweit.logit.database.UserObject
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import javax.servlet.http.HttpServletRequest

import java.io.IOException
import javax.servlet.*

class JWTFilter : Filter {

    @Throws(ServletException::class)
    override fun init(config: FilterConfig?) {
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest, response: ServletResponse?,
        chain: FilterChain
    ) {
        val req = request as HttpServletRequest

        if (req.getHeader(AuthenticationConstants.HEADER_STRING) == null) {
            return chain.doFilter(request, response)
        }
        val splitter: List<String> = req.getHeader(AuthenticationConstants.HEADER_STRING).split(" ")
        val type: String = splitter[0]
        val token: String = splitter[1]
        val algorithm: Algorithm = Algorithm.HMAC256(AuthenticationConstants.SECRET)
        val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(AuthenticationConstants.ISSUER).build()
        val decoded: DecodedJWT = verifier.verify(token)
        if (type == AuthenticationConstants.USER_TYPE_BEARER) {
            var userId: String? = null
            try {
                val type = decoded.claims["type"]!!.asString()
                userId = decoded.claims["userId"]!!.asString()
                if (userId == null || type != AuthenticationConstants.USER_TYPE) {
                    return chain.doFilter(request, response)
                }
            } catch (e: Exception) {
                return chain.doFilter(request, response)
            }
            val currentUser: UserObject = UserObject.getUserByUserId(userId) ?: return chain.doFilter(request, response)
            val authorities: Collection<SimpleGrantedAuthority> =
                arrayListOf(SimpleGrantedAuthority(AuthenticationConstants.USER_TYPE))
            var authentication: UsernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(currentUser.id, null, authorities)
            SecurityContextHolder.getContext().authentication = authentication;
        } else if (type == AuthenticationConstants.PROGRAMMABLE_TOKEN_TYPE_BEARER) {
            var streamId: String? = null
            try {
                val type = decoded.claims["type"]!!.asString()
                streamId = decoded.claims["tokenId"]!!.asString()
                if (streamId == null || type != AuthenticationConstants.PROGRAMMABLE_TOKEN_TYPE) {
                    return chain.doFilter(request, response)
                }
            } catch (e: Exception) {
                return chain.doFilter(request, response)
            }
            val tokenId: String = TokenObject.checkValid(streamId)
            val authorities: Collection<SimpleGrantedAuthority> =
                arrayListOf(SimpleGrantedAuthority(AuthenticationConstants.PROGRAMMABLE_TOKEN_TYPE))
            val authentication: UsernamePasswordAuthenticationToken =
                UsernamePasswordAuthenticationToken(tokenId, null, authorities)
            SecurityContextHolder.getContext().authentication = authentication;
        }
        chain.doFilter(request, response)

    }

    override fun destroy() {
        // cleanup code, if necessary
    }
}