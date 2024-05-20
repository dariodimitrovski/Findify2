package com.sorsix.finalproject.backend.authentication.userDetailsService

import com.sorsix.finalproject.backend.authentication.UserSecurity
import com.sorsix.finalproject.backend.domain.User
import com.sorsix.finalproject.backend.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class JpaUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails? =
        userRepository.findByEmail(email)?.let {
//            it.password = "" //TODO: Remove if unused
            return UserSecurity(it)
//        }
//        userRepository.findByEmail(email = email)?.let {
//            return UserSecurity(it)
//        }
        }
}
