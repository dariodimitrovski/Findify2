package com.sorsix.finalproject.backend.service.implementation

import com.sorsix.finalproject.backend.domain.Comment
import com.sorsix.finalproject.backend.repository.CommentRepository
import com.sorsix.finalproject.backend.service.CommentService
import org.springframework.stereotype.Service

@Service
class CommentServiceImpl(private val commentRepository: CommentRepository) : CommentService {
    override fun findByPostId(postId: Long): List<Comment> = commentRepository.findByPostId(postId)
    override fun createComment(comment: Comment) = commentRepository.save(comment)
}