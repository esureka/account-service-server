package esperer.userservicekotlin.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurity : WebSecurityConfigurerAdapter() {

    @Bean
    override fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.authorizeRequests().antMatchers("/users/**")
            .hasIpAddress("192.168.0.8")
            .and()
            .addFilter(getAuthenticationFilter())
        http.headers().frameOptions().disable()

        return http.build()
    }

    private fun getAuthenticationFilter(): AuthenticationFilter {
        val authenticationFilter = AuthenticationFilter()
        authenticationFilter.setAuthenticationManager(authenticationManager())

        return authenticationFilter
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()

}