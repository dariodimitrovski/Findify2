package com.sorsix.finalproject.backend.domain

import jakarta.persistence.*

@Entity
@Table(name = "comments")
data class Comment(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 1L,
    @Column
    val comment: String = "",
    @ManyToOne
    @JoinColumn(name = "post_id")
    val post: Post = Post(),
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User()
)


