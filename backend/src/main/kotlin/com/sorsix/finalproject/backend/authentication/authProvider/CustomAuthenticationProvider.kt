package com.sorsix.finalproject.backend.authentication.authProvider

import com.sorsix.finalproject.backend.authentication.CustomPrincipal
import com.sorsix.finalproject.backend.authentication.service.TokenService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationProvider(private val tokenService: TokenService): AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication? {
        if(authentication !is BearerTokenAuthenticationToken){
            return null
        }

        val jwt: BearerTokenAuthenticationToken = authentication
        val token: String = jwt.token
        val userDetails: UserDetails = tokenService.parseToken(token) ?: throw BadCredentialsException("Invalid token")

        val userId = tokenService.getUserIdFromToken(token) // Implement this method to extract userId from the token.

        return UsernamePasswordAuthenticationToken(
            CustomPrincipal(userDetails, userId),
            "",
            listOf(SimpleGrantedAuthority("USER"))
        )

    }

    override fun supports(authentication: Class<*>?): Boolean {
        return BearerTokenAuthenticationToken::class.java.isAssignableFrom(authentication)

    }
}