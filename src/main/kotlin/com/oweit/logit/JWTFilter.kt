



import javax.servlet.http.HttpServletRequest

import java.io.IOException
import java.util.logging.Logger
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
        println("Filter in progress")
        val req = request as HttpServletRequest
        chain.doFilter(request, response)
    }

    override fun destroy() {
        // cleanup code, if necessary
    }
}