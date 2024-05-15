package com.sorsix.finalproject.backend.service.implementation

import com.sorsix.finalproject.backend.domain.Location
import com.sorsix.finalproject.backend.repository.LocationRepository
import com.sorsix.finalproject.backend.service.LocationService
import org.springframework.stereotype.Service

@Service
class LocationServiceImpl(private val locationRepository: LocationRepository) : LocationService {
    override fun findById(id: Long): Location? = locationRepository.findById(id).orElse(null)
    override fun save(location: Location): Location = locationRepository.save(location)
    override fun findLatest(): Location? = locationRepository.findFirstByOrderByIdDesc().orElse(null)
}