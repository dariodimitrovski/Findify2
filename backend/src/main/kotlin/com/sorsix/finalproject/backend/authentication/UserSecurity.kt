package com.sorsix.finalproject.backend.authentication

import com.sorsix.finalproject.backend.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserSecurity(user: User) : UserDetails {

    private val email: String = user.email
    private val password: String = user.password
    private val authorities: MutableCollection<GrantedAuthority> =
        mutableListOf(SimpleGrantedAuthority(user.role.toString()))


    override fun getAuthorities(): Collection<GrantedAuthority> {
//        return AuthorityUtils.createAuthorityList("USER")
        return authorities
    }

    override fun getUsername(): String {
        return email
    }

    override fun getPassword(): String {
        return password
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

}