package com.sorsix.finalproject.backend.authentication.service

import com.sorsix.finalproject.backend.domain.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.oauth2.jwt.*
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit

@Service
class TokenService(
    private val jwtDecoder: JwtDecoder,
    private val jwtEncoder: JwtEncoder,
    private val userDetailsService: UserDetailsService
) {

    fun createToken(user: User): String {
        val jwsHeader = JwsHeader.with { "HS256" }.build()
        val claims = JwtClaimsSet.builder()
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plus(30L, ChronoUnit.DAYS))
            .subject(user.firstName)
            .claim("userId", user.id)
            .claim("email", user.email)
            .build()
        return jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader, claims)).tokenValue
    }

    fun parseToken(token: String): UserDetails? {
        return try {
            val jwt = jwtDecoder.decode(token)
//            val id = jwt.claims["userId"].toString()
            val email = jwt.claims["email"].toString()
//            userDetailsService.loadUserByUsername(id)
            userDetailsService.loadUserByUsername(email)
        } catch (e: Exception) {
            null
        }
    }

    fun getUserIdFromToken(token: String): Long {
        return jwtDecoder.decode(token).claims["userId"] as Long
    }

}