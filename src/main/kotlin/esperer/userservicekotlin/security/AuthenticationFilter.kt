package esperer.userservicekotlin.security

import com.fasterxml.jackson.databind.ObjectMapper
import esperer.userservicekotlin.vo.RequestLogin
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.lang.RuntimeException
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter : UsernamePasswordAuthenticationFilter() {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        try {
            val credentials = ObjectMapper().readValue(request.inputStream, RequestLogin::class.java)

            return authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(credentials.email, credentials.password, listOf())
            )

        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun successfulAuthentication(
        request: HttpServletRequest?,
        response: HttpServletResponse?,
        chain: FilterChain?,
        authResult: Authentication?,
    ) {

    }
}