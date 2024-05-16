package com.sorsix.finalproject.backend.authentication.filter

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val authenticationManager: AuthenticationManager) : OncePerRequestFilter() {
    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val jwt: String = request.getHeader("Authorization").substringAfter("Bearer ")
            val authentication = BearerTokenAuthenticationToken(jwt)
            val authResult = authenticationManager.authenticate(authentication)
            SecurityContextHolder.getContext().authentication = authResult
            filterChain.doFilter(request, response)
        } catch (e: Exception) {
            //response.status = 401
            println(e.message)
            return
        }
    }

    override fun shouldNotFilter(request: HttpServletRequest): Boolean {
        return request.servletPath.equals("/api/auth/login") ||
                request.servletPath.equals("/api/auth/register") ||
//                request.servletPath.equals("/api/home") ||
                request.servletPath.equals("/api/municipalities") ||
                request.servletPath.equals("/api/users") ||
//                request.servletPath.equals("/api/user/get") ||
                request.servletPath.equals("/api/posts") ||
                request.servletPath.equals("/api/posts/lost-items") ||
                request.servletPath.equals("/api/posts/found-items") ||
                request.servletPath.equals("/api/categories") ||
//                request.servletPath.startsWith("/api/posts/") ||
                request.servletPath.startsWith("/api") && request.servletPath.endsWith("/image")
    }


}