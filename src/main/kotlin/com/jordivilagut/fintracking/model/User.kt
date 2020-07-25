package com.jordivilagut.fintracking.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document("users")
data class User(
        @Id val id: ObjectId?,
        val email: String,
        val password: String,
        var token: String?)