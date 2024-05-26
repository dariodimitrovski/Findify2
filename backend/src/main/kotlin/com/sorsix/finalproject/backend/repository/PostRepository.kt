package com.sorsix.finalproject.backend.repository

import com.sorsix.finalproject.backend.domain.Category
import com.sorsix.finalproject.backend.domain.Municipality
import com.sorsix.finalproject.backend.domain.Post
import com.sorsix.finalproject.backend.domain.PostStatus
import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : PagingAndSortingRepository<Post, Long>, JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE Post p SET p.state = :state WHERE p.id = :id")
    fun updateStateById(id: Long, state: PostStatus): Int
    fun findAllByCategoryAndState(category: Category, state: PostStatus): List<Post>
    fun findAllByMunicipalityAndState(municipality: Municipality, state: PostStatus): List<Post>

    fun findAllByTitleContainingIgnoreCaseAndCategoryAndState(
        title: String,
        category: Category,
        state: PostStatus
    ): List<Post>

    fun findAllByTitleContainingIgnoreCaseAndMunicipalityAndState(
        title: String,
        municipality: Municipality,
        state: PostStatus
    ): List<Post>

    fun findAllByCategoryAndMunicipalityAndState(
        category: Category,
        municipality: Municipality,
        state: PostStatus
    ): List<Post>

    fun findAllByTitleContainingIgnoreCaseAndState(title: String, status: PostStatus): List<Post>
    fun findAllByState(status: PostStatus): List<Post>

    fun findAllByTitleContainingIgnoreCaseAndCategoryAndMunicipalityAndState(
        title: String,
        category: Category,
        municipality: Municipality,
        state: PostStatus
    ): List<Post>

    fun findAllByState(status: PostStatus, pageable: Pageable): Page<Post>
}