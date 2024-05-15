package com.sorsix.finalproject.backend.api

import com.sorsix.finalproject.backend.domain.Category
import com.sorsix.finalproject.backend.domain.Municipality
import com.sorsix.finalproject.backend.service.CategoryService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
@CrossOrigin
class CategoryController(private val categoryService: CategoryService) {

    @GetMapping("/categories")
    fun getCategories(): ResponseEntity<List<Category>> {
        return ResponseEntity.ok().body(categoryService.listAll())
    }
}