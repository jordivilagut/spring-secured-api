package com.jordivilagut.fintracking.security

import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter


@Configuration
@EnableWebSecurity
class SecurityConfig : WebSecurityConfigurerAdapter() {

    companion object {
        private const val LOGIN_URL = "/auth/login"
    }

    /*override fun configure(auth: AuthenticationManagerBuilder) {
        auth.authenticationProvider(provider)
    }*/

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests()
                .antMatchers(LOGIN_URL).permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
    }
}