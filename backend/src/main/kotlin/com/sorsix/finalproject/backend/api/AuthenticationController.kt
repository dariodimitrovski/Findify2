package com.sorsix.finalproject.backend.api

import com.sorsix.finalproject.backend.authentication.service.HashService
import com.sorsix.finalproject.backend.authentication.service.TokenService
import com.sorsix.finalproject.backend.domain.dto.LoginDto
import com.sorsix.finalproject.backend.domain.dto.LoginResponseDto
import com.sorsix.finalproject.backend.domain.dto.RegisterDto
import com.sorsix.finalproject.backend.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
class AuthenticationController(
    private val hashService: HashService,
    private val tokenService: TokenService,
    private val userService: UserService
) {

    @PostMapping("/login")
    fun login(@Valid @RequestBody payload: LoginDto): ResponseEntity<*> {
        val user = userService.findByEmail(payload.email)?.takeIf {
            hashService.checkBcrypt(payload.password, it.password)
        }

        return user?.let { ResponseEntity.ok(LoginResponseDto(user = user, token = tokenService.createToken(it))) }
            ?: ResponseEntity.badRequest().body("Invalid Credentials.")
    }

    @PostMapping("/register")
    fun register(@Valid @RequestBody payload: RegisterDto): ResponseEntity<*> {
        if (userService.existsByEmail(payload.email)) {
            return ResponseEntity.badRequest().body("A user with this email already exists.")
        }
        val savedUser = userService.registerUser(
            payload.firstName,
            payload.lastName,
            payload.email,
            payload.password,
            payload.phoneNumber
        )
        return ResponseEntity.ok(LoginResponseDto(user = savedUser, token = tokenService.createToken(savedUser)))
    }


}