package com.sorsix.finalproject.backend.service

import com.sorsix.finalproject.backend.domain.*
import org.springframework.data.domain.Page
import org.springframework.web.multipart.MultipartFile

interface PostService {
    fun listAll(): List<Post>
    //fun findByStatus(status: PostStatus): List<Post>
    fun findById(id: Long): Post?
    fun updateState(id: Long, newState: PostStatus): Post?
    fun deleteById(id: Long)
    fun create(title: String, category: Category, description: String, municipality: Municipality, image: MultipartFile, status: PostStatus, location: Location, time: String): Post

    fun filter(title: String, category: Category?, municipality: Municipality?, status: PostStatus, order: String): List<Post>

    fun getPostImage(postId: Long): ByteArray

    ///

    fun findByStatus(status: PostStatus, page: Int, size: Int): Page<Post>

}