package com.sorsix.finalproject.backend.repository

import com.sorsix.finalproject.backend.domain.Location
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface LocationRepository: JpaRepository<Location, Long> {
    override fun findById(id: Long): Optional<Location>
    fun findFirstByOrderByIdDesc(): Optional<Location>
}