package com.sorsix.finalproject.backend.service

import com.sorsix.finalproject.backend.domain.Location

interface LocationService {
    fun findById(id: Long): Location?
    fun save(location: Location): Location
    fun findLatest(): Location?
}