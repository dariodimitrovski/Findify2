package com.sorsix.finalproject.backend.repository

import com.sorsix.finalproject.backend.domain.Category
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository:  JpaRepository<Category, Long>{
    fun findByCategoryName(name: String): Category?

}