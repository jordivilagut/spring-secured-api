package com.jordivilagut.fintracking.security

import com.jordivilagut.fintracking.exceptions.InvalidUserException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

@Component
class UserAuthProvider: AbstractUserDetailsAuthenticationProvider() {

    override fun retrieveUser(username: String, auth: UsernamePasswordAuthenticationToken): UserDetails {
        return  if (auth.principal != null) auth.principal as UserDetails
                else throw InvalidUserException("Unauthorised")
    }

    override fun additionalAuthenticationChecks(details: UserDetails, auth: UsernamePasswordAuthenticationToken) {
        //Do nothing
    }
}