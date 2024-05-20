package com.sorsix.finalproject.backend.config

import com.sorsix.finalproject.backend.authentication.authProvider.CustomAuthenticationProvider
import com.sorsix.finalproject.backend.authentication.filter.JwtAuthenticationFilter
import com.sorsix.finalproject.backend.authentication.service.TokenService
import com.sorsix.finalproject.backend.domain.Role
import org.springframework.beans.factory.annotation.Autowired
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
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher


@Configuration
@EnableWebSecurity
class SecurityConfig(private val customAuthenticationProvider: CustomAuthenticationProvider, private val tokenService: TokenService, private val userDetailsService: UserDetailsService) {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(customAuthenticationProvider))
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(authenticationManager(),tokenService, userDetailsService)
    }



    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    // Permit access to specific endpoints without authentication
                    .requestMatchers("/api/auth/**", "/api/posts/**", "/api/categories", "/api/municipalities", "/api/users/image/**").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                    .requestMatchers("/api/posts/add/new-post").hasAnyRole("USER", "ADMIN")
//                    .requestMatchers(HttpMethod.GET, "/api/posts/**").permitAll()
//                   .requestMatchers(HttpMethod.POST, "/api/posts/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/users/**").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
//                    .requestMatchers(HttpMethod.GET,"/api/municipalities").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/comments/**").permitAll()
                    // Require authentication for other endpoints
                    .anyRequest().authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(customAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }







}