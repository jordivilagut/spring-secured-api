package com.jordivilagut.fintracking.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("hello")
class SecuredControllerImpl : SecuredController {

    @GetMapping
    override fun sayHi(): String {
        return "Hello World!"
    }
}