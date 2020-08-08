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
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource


@Configuration
@EnableWebSecurity
class SecurityConfig

    constructor(
        @Autowired val userService: UserService,
        @Autowired val authProvider: UserAuthProvider)

    : WebSecurityConfigurerAdapter() {

    companion object {
        private const val LOGIN_URL = "/auth/login"
        private const val LOCAL_URI = "http://localhost:3000"
    }

    override fun configure(http: HttpSecurity) {
        http
                .cors().configurationSource(corsConfigurationSource())
                .and().authenticationProvider(authProvider)
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

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource? {
        val source = UrlBasedCorsConfigurationSource()
        val corsConfiguration = CorsConfiguration().applyPermitDefaultValues()
        corsConfiguration.allowedOrigins = arrayListOf(LOCAL_URI)
        source.registerCorsConfiguration("/**", corsConfiguration)
        return source
    }
}