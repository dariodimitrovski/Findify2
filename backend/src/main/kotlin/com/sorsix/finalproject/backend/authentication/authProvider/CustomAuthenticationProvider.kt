package com.sorsix.finalproject.backend.authentication.authProvider

import com.sorsix.finalproject.backend.authentication.UserSecurity
import com.sorsix.finalproject.backend.authentication.service.TokenService
import com.sorsix.finalproject.backend.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component


@Component
class CustomAuthenticationProvider(private val tokenService: TokenService, private val userService: UserService) :
    AuthenticationProvider {
    override fun authenticate(authentication: Authentication): Authentication? {
        if (authentication !is BearerTokenAuthenticationToken) {
            return null
        }
        val jwt: BearerTokenAuthenticationToken = authentication
        val token: String = jwt.token
        val user = userService.findByEmail(tokenService.getUserEmailFromToken(token))!!

        return UsernamePasswordAuthenticationToken(
            UserSecurity(user),
            "",
            listOf(SimpleGrantedAuthority("USER"))
        )

    }

    override fun supports(authentication: Class<*>?): Boolean {
        return BearerTokenAuthenticationToken::class.java.isAssignableFrom(authentication)

    }
}