package com.oweit.logit.security



import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
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
        val token: String = req.getHeader(AuthenticationConstants.HEADER_STRING).split(" ")[1]
        val algorithm: Algorithm = Algorithm.HMAC256(AuthenticationConstants.SECRET)
        var decoded: DecodedJWT? = null
        var userName: String? = null
        try {
            val verifier: JWTVerifier = JWT.require(algorithm).withIssuer(AuthenticationConstants.ISSUER).build()
            decoded = verifier.verify(token)
            userName = decoded!!.claims["username"]!!.asString()
            if (userName == null) {
                return chain.doFilter(request, response)
            }
        } catch (e: Exception) {
            return chain.doFilter(request, response)
        }
        var authentication: UsernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(userName, null, ArrayList())
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response)
    }

    override fun destroy() {
        // cleanup code, if necessary
    }
}