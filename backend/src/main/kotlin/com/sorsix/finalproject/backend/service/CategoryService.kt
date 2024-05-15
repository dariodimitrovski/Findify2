package com.sorsix.finalproject.backend.service

import com.sorsix.finalproject.backend.domain.Category

interface CategoryService {

    fun listAll(): List<Category>
    fun findCategoryByName(name: String): Category?

    fun findById(id: Long): Category?
}