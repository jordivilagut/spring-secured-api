package com.jordivilagut.fintracking.controllers

import com.jordivilagut.fintracking.model.dto.Auth
import com.jordivilagut.fintracking.model.dto.UserCredentials

interface AuthenticationController {

    fun login(token: String, credentials: UserCredentials): Auth

    fun signup(credentials: UserCredentials): Auth
}