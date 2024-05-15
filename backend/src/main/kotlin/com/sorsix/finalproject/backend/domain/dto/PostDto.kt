package com.sorsix.finalproject.backend.domain.dto

import com.sorsix.finalproject.backend.domain.Category
import com.sorsix.finalproject.backend.domain.Municipality
import com.sorsix.finalproject.backend.domain.PostStatus
import com.sorsix.finalproject.backend.domain.User

//needs to be deleted
data class PostDto(
    val title: String,
    val category: Category,
    val description: String,
    val municipality: Municipality,
    val image: ByteArray,
    val state: PostStatus
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PostDto

        if (title != other.title) return false
        if (category != other.category) return false
        if (description != other.description) return false
        if (municipality != other.municipality) return false
        if (!image.contentEquals(other.image)) return false
        if (state != other.state) return false

        return true
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + category.hashCode()
        result = 31 * result + description.hashCode()
        result = 31 * result + municipality.hashCode()
        result = 31 * result + image.contentHashCode()
        result = 31 * result + state.hashCode()
        return result
    }
}
