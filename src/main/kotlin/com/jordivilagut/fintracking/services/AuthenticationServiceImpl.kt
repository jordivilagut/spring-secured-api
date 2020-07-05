package com.jordivilagut.fintracking.services

import com.jordivilagut.fintracking.exceptions.InvalidUserException
import com.jordivilagut.fintracking.model.dto.Auth
import com.jordivilagut.fintracking.model.dto.UserCredentials
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AuthenticationServiceImpl

    @Autowired
    constructor(
            val userService: UserService)

    : AuthenticationService {

    companion object {
        val EMAIL_REGEX = Regex("^(.+)@(.+)$")
    }

    override fun login(token: String?, credentials: UserCredentials): Auth {
        return  if (!token.isNullOrBlank()) autoLogin(token)
                else credentialsLogin(credentials)
    }

    override fun register(credentials: UserCredentials): Auth {
        val user = userService.createUser(credentials)
        return login(null, UserCredentials(user.email, user.password))
    }

    private fun autoLogin(token: String): Auth {
        val user = userService.findByToken(token) ?: throw InvalidUserException("User not found.")

        //TODO - Generate and store token for user
        return Auth(user.email, "test-token")
    }

    private fun credentialsLogin(credentials: UserCredentials): Auth {
        if (credentials.email == null)                              throw InvalidUserException("Invalid email.")
        val user = userService.findByEmail(credentials.email) ?:    throw InvalidUserException("Invalid email.")
        if (user.password != credentials.password)                  throw InvalidUserException("Invalid password.")

        //TODO - Generate and store token for user
        return Auth(user.email, "test-token")
    }
}