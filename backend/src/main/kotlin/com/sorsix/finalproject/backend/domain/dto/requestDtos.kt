package com.sorsix.finalproject.backend.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.Size

data class LoginDto(
    @get: Email(message = "The entered email is not a valid email.")
    val email: String,

    @get: Size(min = 6, message = "Password length must be bigger than 6 characters.")
    val password: String,
)

data class RegisterDto(
    val firstName: String,
    val lastName: String,
    val phoneNumber: String,
    @get: Size(min = 6, message = "Password length must be bigger than 6 characters.")
    val password: String,
    @get: Email(message = "The entered email is not a valid email.")
    val email: String
)
