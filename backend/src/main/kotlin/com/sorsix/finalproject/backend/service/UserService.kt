package com.sorsix.finalproject.backend.service


import com.sorsix.finalproject.backend.domain.User
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.multipart.MultipartFile

interface UserService {
    fun findById(id: Long): User?

    fun findByEmail(email: String): User?

    fun existsByEmail(email: String): Boolean

    fun registerUser(firstName: String,lastName: String, email: String,  password: String, phoneNumber: String): User

    fun listUsers(): List<User>

    fun deleteById(id: Long)

//    fun updateUserData(id: Long, firstName: String, lastName: String, phoneNumber: String, password: String)


    fun updateProfile(
        firstName: String?, lastName: String?, password: String?,
        image: MultipartFile?, phoneNumber: String?
    ): User

    fun getUserImage(): ByteArray

}