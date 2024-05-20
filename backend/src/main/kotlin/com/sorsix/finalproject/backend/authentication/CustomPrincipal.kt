package com.sorsix.finalproject.backend.authentication

import com.sorsix.finalproject.backend.domain.Role
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails

data class CustomPrincipal(
    private val userDetails: UserDetails,
    val userId: Long
) : Authentication {

    private val authorities: MutableCollection<GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(Role.values().toString()))


    override fun getName(): String {
        return userDetails.username
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun getCredentials(): Any {
        return ""
    }

    override fun getDetails(): Any {
        return ""
    }

    override fun getPrincipal(): Any {
        return userDetails
    }

    override fun isAuthenticated(): Boolean {
        return true
    }

    override fun setAuthenticated(isAuthenticated: Boolean) {
    }


}
