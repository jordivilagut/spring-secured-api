package com.jordivilagut.fintracking.security

import org.springframework.security.web.RedirectStrategy
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class NoRedirect: RedirectStrategy {

    override fun sendRedirect(request: HttpServletRequest, response: HttpServletResponse, url: String) {
        //Do nothing
    }
}