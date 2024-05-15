package com.sorsix.finalproject.backend.repository

import com.sorsix.finalproject.backend.domain.User
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByEmail(email: String?): User?
    fun existsByEmail(email: String): Boolean
    override fun findAll():List<User>
    override fun deleteById(id: Long)
//    @Transactional
//    @Modifying
//    @Query("UPDATE User u SET u.firstName = :firstName, u.lastName = :lastName, u.phoneNumber = :phoneNumber, u.password = :password WHERE u.id = :id")
//    fun updateUserData(id: Long, firstName: String, lastName: String, phoneNumber: String, password: String)

    @Modifying
    @Transactional
    @Query("update User u set u.firstName = :name where u.id = :userId")
    fun updateName(userId: Long, name: String)

    @Modifying
    @Transactional
    @Query("update User u set u.lastName = :surname where u.id = :userId")
    fun updateSurname(userId: Long, surname: String)

    @Modifying
    @Transactional
    @Query("update User u set u.password = :password where u.id = :userId")
    fun updatePassword(userId: Long, password: String)

    @Modifying
    @Transactional
    @Query("update User u set u.image = :byteArr where u.id = :userId")
    fun updateImage(userId: Long, byteArr: ByteArray)

    @Modifying
    @Transactional
    @Query("update User u set u.phoneNumber = :number where u.id = :userId")
    fun updateNumber(userId: Long, number: String)

}