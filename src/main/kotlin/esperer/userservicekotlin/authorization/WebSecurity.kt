package esperer.userservicekotlin.authorization

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class WebSecurity {

    @Bean
    protected fun configure(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable()
        http.authorizeRequests().antMatchers("/users/**").permitAll()
        http.headers().frameOptions().disable()

        return http.build()
    }

    @Bean
    protected fun passwordEncoder() = BCryptPasswordEncoder()

}