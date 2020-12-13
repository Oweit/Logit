package com.oweit.logit.api

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/log")
class LogController {

    @PostMapping
    fun log(): String {
        return "ok"
    }
}