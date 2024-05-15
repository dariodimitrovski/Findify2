package com.sorsix.finalproject.backend.config

import com.sorsix.finalproject.backend.authentication.authProvider.CustomAuthenticationProvider
import com.sorsix.finalproject.backend.authentication.filter.JwtAuthenticationFilter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter


@Configuration
@EnableWebSecurity
class SecurityConfig(private val customAuthenticationProvider: CustomAuthenticationProvider) {

    @Bean
    fun authenticationManager(): AuthenticationManager {
        return ProviderManager(listOf(customAuthenticationProvider))
    }

    @Bean
    fun jwtAuthenticationFilter(): JwtAuthenticationFilter {
        return JwtAuthenticationFilter(authenticationManager())
    }

//    @Bean
//    fun filterChain(http: HttpSecurity): SecurityFilterChain {
//        http.securityMatcher("/api/**")
//            .authorizeHttpRequests { rmr ->
//                rmr
//                    .requestMatchers(HttpMethod.POST, "/api/login").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/api/register").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/home").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/municipalities").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/user/get").permitAll()
//                    .requestMatchers(HttpMethod.POST, "/api/new-post").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/lost-items").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/found-items").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/categories").permitAll()
//                    .requestMatchers(HttpMethod.GET, "/api/{}/image}").permitAll()
////                    .requestMatchers(HttpMethod.GET, "/api/{userId}/image").permitAll()
//
//                    .requestMatchers("/api/**").authenticated()
//                    .anyRequest().permitAll()
//            }
//            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
//            .csrf { it.disable() }
//
//        http.oauth2ResourceServer { oauth2 ->
//            oauth2
//                .jwt(Customizer.withDefaults())
//        }
//
//        http.addFilterAt(jwtAuthenticationFilter(), BasicAuthenticationFilter::class.java)
//        return http.build()
//    }
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf { it.disable() }
            .authorizeHttpRequests {
                it
                    .requestMatchers(
                        "**"
// "/api/auth/**",
// "/api/artists",
// "/api/categories",
// "/api/countries",
// "/api/uploads"
                    )
                    .permitAll()
//                    .requestMatchers("/api/new-post")
//                    .authenticated()
            }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authenticationProvider(customAuthenticationProvider)
            .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter::class.java)

        return http.build()
    }

}