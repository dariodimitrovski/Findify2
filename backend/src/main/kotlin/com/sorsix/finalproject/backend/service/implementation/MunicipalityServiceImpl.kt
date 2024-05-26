package com.sorsix.finalproject.backend.service.implementation

import com.sorsix.finalproject.backend.domain.Municipality
import com.sorsix.finalproject.backend.repository.MunicipalityRepository
import com.sorsix.finalproject.backend.service.MunicipalityService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class MunicipalityServiceImpl(private val municipalityRepo: MunicipalityRepository): MunicipalityService {
    override fun listAll(): List<Municipality> {
        return municipalityRepo.findAll()
    }
    override fun findMunicipalityByName(name: String): Municipality? = this.municipalityRepo.findByName(name)
    override fun findById(id: Long): Municipality? {
        return this.municipalityRepo.findByIdOrNull(id)
    }
}