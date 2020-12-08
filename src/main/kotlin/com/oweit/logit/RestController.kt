package com.oweit.logit

import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/")
class RestController {

    @GetMapping("/")
    fun getHome(): String {
        return "This is home"
    }

    @GetMapping("/token")
    fun login(@RequestBody loginDetails: LoginDetails): String {
        return "longjwttoken"
    }
}