package com.sorsix.finalproject.backend.api

import com.sorsix.finalproject.backend.domain.Comment
import com.sorsix.finalproject.backend.service.CommentService
import com.sorsix.finalproject.backend.service.PostService
import com.sorsix.finalproject.backend.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin
@RequestMapping("/api/comments")
class CommentController(private val commentService: CommentService,
                        private val userService: UserService,
                        private val postService: PostService
) {
    @GetMapping("/{id}")
    fun getPostComments(@PathVariable id: Long): ResponseEntity<List<Comment>> {
        return ResponseEntity.ok(commentService.findByPostId(id))
    }
    @PostMapping("/add")
    fun addComment(
        @RequestParam postId: Long,
        @RequestParam comment: String,
        @RequestParam userId: Long
    ): ResponseEntity<Comment> {
        val user = userService.findById(userId)!!
        val post = postService.findById(postId)!!
        val created = commentService.createComment(Comment(id = 1L, comment = comment, post = post, user = user))
        return ResponseEntity.ok(created)
    }
}