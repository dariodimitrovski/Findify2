package com.sorsix.finalproject.backend.config

import com.sorsix.finalproject.backend.authentication.authProvider.CustomAuthenticationProvider
import com.sorsix.finalproject.backend.authentication.filter.JwtAuthenticationFilter
import com.sorsix.finalproject.backend.authentication.service.TokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customAuthenticationProvider: CustomAuthenticationProvider,
    private val tokenService: TokenService,
    private val userDetailsService: UserDetailsService
) {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(customAuthenticationProvider))
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(tokenService, userDetailsService)
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "/api/auth/**",
                        "/api/posts/**",
                        "/api/categories",
                        "/api/municipalities",
                        "/api/users/image/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(customAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }
}
