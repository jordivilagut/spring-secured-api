package com.jordivilagut.fintracking.security

import com.jordivilagut.fintracking.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler
import org.springframework.security.web.util.matcher.AntPathRequestMatcher
import org.springframework.security.web.util.matcher.NegatedRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig

    constructor(
        @Autowired val userService: UserService,
        @Autowired val authProvider: UserAuthProvider)

    : WebSecurityConfigurerAdapter() {

    companion object {
        private const val LOGIN_URL = "/auth/login"
    }

    override fun configure(http: HttpSecurity) {
        http
                .authenticationProvider(authProvider)
                .addFilterBefore(userAuthFilter(), AnonymousAuthenticationFilter::class.java)
                .authorizeRequests()
                    .antMatchers(LOGIN_URL).permitAll()
                    .anyRequest().authenticated()
                .and().csrf().disable()
    }

    @Bean
    @Throws(Exception::class)
    fun userAuthFilter(): UserAuthFilter {
        val filter = UserAuthFilter(userService, NegatedRequestMatcher(AntPathRequestMatcher(LOGIN_URL)))
        filter.setAuthenticationManager(authenticationManager())
        filter.setAuthenticationSuccessHandler(successHandler())
        return filter
    }

    @Bean
    fun successHandler(): SimpleUrlAuthenticationSuccessHandler {
        val successHandler = SimpleUrlAuthenticationSuccessHandler()
        successHandler.setRedirectStrategy(NoRedirect())
        return successHandler
    }
}