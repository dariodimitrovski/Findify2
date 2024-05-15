package com.sorsix.finalproject.backend.service.implementation

import com.sorsix.finalproject.backend.authentication.service.HashService
import com.sorsix.finalproject.backend.domain.User
import com.sorsix.finalproject.backend.repository.UserRepository
import com.sorsix.finalproject.backend.service.UserService
import jakarta.transaction.Transactional
import org.springframework.data.repository.findByIdOrNull
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.client.HttpClientErrorException
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream

@Service
class UserServiceImpl(
    private val userRepo: UserRepository,
    private val hashService: HashService
) : UserService {
    override fun findById(id: Long): User? = userRepo.findByIdOrNull(id)

    override fun findByEmail(email: String): User? = userRepo.findByEmail(email)

    override fun existsByEmail(email: String): Boolean = userRepo.existsByEmail(email)

    override fun listUsers(): List<User> = userRepo.findAll()

    override fun deleteById(id: Long) = userRepo.deleteById(id)

    @Transactional
    override fun updateProfile(
        firstName: String?,
        lastName: String?,
        password: String?,
        image: MultipartFile?,
        phoneNumber: String?
    ): User {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userRepo.findByEmail(email)!!

        if (firstName != null && firstName != "") {
            userRepo.updateName(user.id, firstName)
        }
        if (lastName != null && lastName != "") {
            userRepo.updateSurname(user.id, lastName)
        }
        if (password != null && password != "") {
            userRepo.updatePassword(user.id, hashService.hashBcrypt(password))
        }
        if(phoneNumber != null && phoneNumber != ""){
            userRepo.updateNumber(user.id, phoneNumber)
        }

        val newImage = if (image != null) {
            val byteArr: ByteArray = image.bytes
            ByteArrayInputStream(byteArr)

            userRepo.updateImage(user.id, byteArr)
            byteArr
        } else {
            user.image
        }


        val newPassword = if (password != null) {
            hashService.hashBcrypt(password)
        } else {
            user.password
        }

        val tmp = user.copy(
            id = user.id,
            firstName = firstName ?: user.firstName,
            lastName = lastName ?: user.lastName,
            phoneNumber = phoneNumber ?: user.phoneNumber,
            email = user.email,
            password = newPassword,
            role = user.role,
            image = newImage
        )

        this.userRepo.save(tmp)


        return tmp

    }

    override fun getUserImage(): ByteArray {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userRepo.findByEmail(email)
        return user!!.image
    }

    //override fun updateUserData(id: Long, firstName: String, lastName: String, phoneNumber: String, password: String) = userRepo.updateUserData(id, firstName, lastName, phoneNumber, password)

    override fun registerUser(
        firstName: String,
        lastName: String,
        email: String,
        password: String,
        phoneNumber: String
    ): User = userRepo.save(
        User(
            id = 1L,
            firstName = firstName,
            lastName = lastName,
            email = email,
            password = hashService.hashBcrypt(password),
            phoneNumber = phoneNumber
        )
    )
}