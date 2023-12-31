package esperer.userservicekotlin.security

import com.fasterxml.jackson.databind.ObjectMapper
import esperer.userservicekotlin.service.UserService
import esperer.userservicekotlin.vo.RequestLogin
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.core.env.Environment
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.io.IOException
import java.lang.RuntimeException
import java.util.*
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    private val userService: UserService,
    private val env: Environment
) : UsernamePasswordAuthenticationFilter() {


    private lateinit var authenticationManager: AuthenticationManager

    override fun afterPropertiesSet() {
        super.afterPropertiesSet()
        super.setAuthenticationManager(authenticationManager)
    }

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
        response: HttpServletResponse,
        chain: FilterChain?,
        authResult: Authentication,
    ) {
        val username = (authResult.principal as User).username
        val userDetails = userService.getUserDetailsByEmail(username)

        val token = Jwts.builder()
            .setSubject(userDetails.userId)
            .setExpiration(Date(System.currentTimeMillis() + env.getProperty("token.expiration_time")!!.toLong()))
            .signWith(SignatureAlgorithm.HS512, env.getProperty("token.secret"))
            .compact()

        response.setHeader("token", token)
        response.setHeader("userId", userDetails.userId)
    }
}