package com.sorsix.finalproject.backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "users")
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 1L,
    @Column(name = "first_name")
    val firstName: String = "",
    @Column(name = "last_name")
    val lastName: String = "",
    @Column(name = "phone_number")
    val phoneNumber: String = "",
    @Column
    val email: String = "",
    @Column
    val password: String = "",
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    val role: Role? = Role.ROLE_USER,
    @Column(columnDefinition = "BYTEA", name = "image")
    val image: ByteArray = ByteArray(1)
) {
   // constructor() : this(1L, "Test", "Test", "075123123", "test@test.com", "test123123", Role.ROLE_USER) {}

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as User

        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        return  image.contentHashCode()
    }
}