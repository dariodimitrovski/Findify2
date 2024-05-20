package com.sorsix.finalproject.backend.authentication.filter

import com.sorsix.finalproject.backend.authentication.service.TokenService
import com.sorsix.finalproject.backend.domain.User
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(
//    private val authenticationManager: AuthenticationManager,
    private val tokenService: TokenService,
    private val userDetailsService: UserDetailsService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val authorizationHeader = request.getHeader("Authorization")
        val userEmail: String?
        val jwt: String?
        when (!authorizationHeader.isNullOrBlank() && authorizationHeader.startsWith("Bearer ")) {
            true -> {
                jwt = authorizationHeader.substring(7)
                userEmail = tokenService.getUserEmailFromToken(jwt)
            }

            else -> {
                jwt = null
                userEmail = null
            }
        }

        if (userEmail != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails = this.userDetailsService.loadUserByUsername(userEmail)
// if (jwtUtil.validateToken(jwt, userDetails)) {
            val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.authorities
            )
            usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
            SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
// }
        }
        filterChain.doFilter(request, response)

    }


    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath.startsWith("/api/auth/login") ||
                request.servletPath.startsWith("/api/users/image/undefined") ||
                request.servletPath.startsWith("/api/municipalities") ||
                request.servletPath.startsWith("/api/categories")

    }


}
