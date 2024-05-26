package com.sorsix.finalproject.backend.service.implementation

import com.sorsix.finalproject.backend.domain.*
import com.sorsix.finalproject.backend.repository.PostRepository
import com.sorsix.finalproject.backend.service.PostService
import com.sorsix.finalproject.backend.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PostServiceImpl(private val postRepository: PostRepository, private val userService: UserService) : PostService {
    override fun listAll(): List<Post> = postRepository.findAll()
    override fun findById(id: Long): Post? = postRepository.findByIdOrNull(id)
    override fun deleteById(id: Long) = postRepository.deleteById(id)
    override fun updateState(id: Long, newState: PostStatus): Post? {
        postRepository.updateStateById(id, newState)
        return postRepository.findByIdOrNull(id)
    }

    override fun create(
        title: String,
        category: Category,
        description: String,
        municipality: Municipality,
        image: MultipartFile,
        status: PostStatus,
        location: Location,
        time: String
    ): Post {
        val email = SecurityContextHolder.getContext().authentication.name
        val user = userService.findByEmail(email)
        val byteArr = image.bytes

        return this.postRepository.save(
            Post(
                id = 1L,
                state = status,
                title = title,
                image = byteArr,
                user = user!!,
                municipality = municipality,
                category = category,
                description = description,
                location = location,
                time = time
            )
        )

    }

    fun buildQuery(
        title: String,
        category: Category?,
        municipality: Municipality?,
        status: PostStatus
    ): List<Post> {
        return when {
            title.isNotEmpty() && category != null && municipality != null ->
                postRepository.findAllByTitleContainingIgnoreCaseAndCategoryAndMunicipalityAndState(
                    title,
                    category,
                    municipality,
                    status
                )

            title.isNotEmpty() && category != null ->
                postRepository.findAllByTitleContainingIgnoreCaseAndCategoryAndState(
                    title,
                    category,
                    status
                )

            title.isNotEmpty() && municipality != null ->
                postRepository.findAllByTitleContainingIgnoreCaseAndMunicipalityAndState(
                    title,
                    municipality,
                    status
                )

            title.isNotEmpty() ->
                postRepository.findAllByTitleContainingIgnoreCaseAndState(title, status)

            category != null && municipality != null ->
                postRepository.findAllByCategoryAndMunicipalityAndState(category, municipality, status)

            category != null ->
                postRepository.findAllByCategoryAndState(category, status)

            municipality != null ->
                postRepository.findAllByMunicipalityAndState(municipality, status)

            else ->
                postRepository.findAllByState(status)
        }
    }

    override fun filter(
        title: String,
        category: Category?,
        municipality: Municipality?,
        status: PostStatus,
        order: String
    ): List<Post> {
        val isAscendingOrder = order != "Најнови прво"

        val posts = if (category == null && municipality == null && title.isEmpty()) {
            postRepository.findAllByState(status)
        } else {
            buildQuery(title, category, municipality, status).distinct()
        }

        val sortedPosts = posts.sortedByDescending { it.date }
        return if (isAscendingOrder) sortedPosts.reversed() else sortedPosts
    }

    override fun getPostImage(postId: Long): ByteArray {
        val post: Post = postRepository.findById(postId).get()

        return post.image
    }

    override fun findByStatus(status: PostStatus, page: Int, size: Int): Page<Post> {
        return this.postRepository.findAllByState(status, PageRequest.of(page, size))
    }

}