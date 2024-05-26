package com.sorsix.finalproject.backend.domain.dto

import com.sorsix.finalproject.backend.domain.User

data class LoginResponseDto(
    val token: String,
    val user: User
)

