package com.sorsix.finalproject.backend.service.implementation

import com.sorsix.finalproject.backend.domain.Category
import com.sorsix.finalproject.backend.repository.CategoryRepository
import com.sorsix.finalproject.backend.service.CategoryService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl(private val categoryRepo: CategoryRepository): CategoryService {

    override fun listAll(): List<Category> = this.categoryRepo.findAll()

    override fun findCategoryByName(name: String): Category? = this.categoryRepo.findByCategoryName(name)
    override fun findById(id: Long): Category? {
        return this.categoryRepo.findByIdOrNull(id)
    }
}