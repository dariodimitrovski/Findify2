package com.sorsix.finalproject.backend.service

import com.sorsix.finalproject.backend.domain.Municipality

interface MunicipalityService {
    fun listAll(): List<Municipality>
    fun findMunicipalityByName(name: String): Municipality?

    fun findById(id: Long): Municipality?

}