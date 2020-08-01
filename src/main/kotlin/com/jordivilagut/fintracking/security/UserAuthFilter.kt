package com.jordivilagut.fintracking.security

import com.jordivilagut.fintracking.exceptions.InvalidUserException
import com.jordivilagut.fintracking.services.UserService
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


class UserAuthFilter(
        private val userService: UserService,
        matcher: RequestMatcher)

    : AbstractAuthenticationProcessingFilter(matcher) {

    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {
        val token = request.getHeader(AUTHORIZATION)
        val user = userService.findByToken(token)
        val auth = UsernamePasswordAuthenticationToken(user, token)
        return authenticationManager.authenticate(auth)
    }

    @Throws(IOException::class, ServletException::class)
    override fun successfulAuthentication(
            request: HttpServletRequest,
            response: HttpServletResponse,
            chain: FilterChain,
            authResult: Authentication) {

        super.successfulAuthentication(request, response, chain, authResult)
        chain.doFilter(request, response)
    }

    override fun unsuccessfulAuthentication(request: HttpServletRequest,
                                            response: HttpServletResponse,
                                            failed: AuthenticationException) {

        throw InvalidUserException("Unsuccessful authentication.")
    }
}