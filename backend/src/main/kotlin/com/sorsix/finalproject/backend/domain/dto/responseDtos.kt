package com.sorsix.finalproject.backend.domain.dto

import com.sorsix.finalproject.backend.domain.User
import org.springframework.http.ResponseEntity

data class LoginResponseDto(
    val token: String,
    val user: User
)

//data class BadRequest(val result: String)