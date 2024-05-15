package com.sorsix.finalproject.backend.service

import com.sorsix.finalproject.backend.domain.Comment

interface CommentService {
    fun findByPostId(postId: Long): List<Comment>
    fun createComment(comment: Comment): Comment
}