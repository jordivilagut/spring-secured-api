package com.jordivilagut.fintracking.controllers

import com.jordivilagut.fintracking.model.dto.Auth
import com.jordivilagut.fintracking.model.dto.UserCredentials
import com.jordivilagut.fintracking.services.AuthenticationService
import com.jordivilagut.fintracking.utils.Headers.Companion.AUTH_TOKEN
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("auth")
class AuthenticationControllerImpl

    @Autowired
    constructor(
            val authService: AuthenticationService)

    : AuthenticationController {

    @PostMapping("/login")
    override fun login(
            @RequestHeader(AUTH_TOKEN) token: String,
            @RequestBody credentials: UserCredentials)

    : Auth {

            return authService.login(token, credentials)
    }

    @PostMapping("/signup")
    override fun signup(
            @RequestBody credentials: UserCredentials)

    : Auth {

        return authService.register(credentials)
    }
}