package com.jordivilagut.fintracking.security

import com.jordivilagut.fintracking.services.UserService
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter
import org.springframework.security.web.util.matcher.RequestMatcher
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
}