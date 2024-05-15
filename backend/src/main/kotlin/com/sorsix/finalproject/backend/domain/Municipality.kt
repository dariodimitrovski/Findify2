package com.sorsix.finalproject.backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "municipalities")
data class Municipality(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 0,
    @Column
    val name: String = ""
)
