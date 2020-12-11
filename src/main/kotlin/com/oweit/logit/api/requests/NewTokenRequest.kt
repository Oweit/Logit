package com.oweit.logit.api.requests

import javax.validation.constraints.NotBlank

class NewTokenRequest(var name: @NotBlank String) {
}