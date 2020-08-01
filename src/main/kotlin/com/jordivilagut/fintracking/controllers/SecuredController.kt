package com.jordivilagut.fintracking.controllers

import com.jordivilagut.fintracking.model.User


interface SecuredController {

    fun sayHi(user: User): String
}