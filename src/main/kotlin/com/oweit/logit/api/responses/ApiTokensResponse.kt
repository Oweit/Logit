package com.oweit.logit.api.responses

import org.bson.Document

class ApiTokensResponse {
    var token: String
    var name: String

    constructor(_token: String) : this(_token, "MissingName"){
    }

    constructor(_token: String, _name: String) {
        name = _name
        token = _token
    }

    constructor(doc: Document) {
        name = doc.getString("name")
        token = doc.getString("token")
    }
}