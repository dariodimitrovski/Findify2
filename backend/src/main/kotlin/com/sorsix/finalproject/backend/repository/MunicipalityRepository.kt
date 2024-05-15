package com.sorsix.finalproject.backend.repository

import com.sorsix.finalproject.backend.domain.Municipality
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MunicipalityRepository: JpaRepository<Municipality, Long>{

    fun findByName(name: String): Municipality?

}