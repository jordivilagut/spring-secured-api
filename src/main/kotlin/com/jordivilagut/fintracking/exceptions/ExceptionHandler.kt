package com.jordivilagut.fintracking.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import javax.servlet.http.HttpServletRequest


@ControllerAdvice
class ExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = [Exception::class])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun defaultErrorHandler(req: HttpServletRequest, e: Exception): String? {
        return e.message
    }
}