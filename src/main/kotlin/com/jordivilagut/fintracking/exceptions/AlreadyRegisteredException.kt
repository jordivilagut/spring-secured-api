package com.jordivilagut.fintracking.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class AlreadyRegisteredException(message: String): RuntimeException(message)