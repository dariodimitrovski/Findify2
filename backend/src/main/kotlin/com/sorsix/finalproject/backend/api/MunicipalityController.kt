package com.sorsix.finalproject.backend.api

import com.sorsix.finalproject.backend.domain.Municipality
import com.sorsix.finalproject.backend.service.MunicipalityService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@CrossOrigin
@RestController
@RequestMapping("/api/municipalities")
class MunicipalityController(private val municipalityService: MunicipalityService) {
    @GetMapping()
    fun getMunicipalities(): ResponseEntity<List<Municipality>> {
        return ResponseEntity.ok().body(municipalityService.listAll())
    }
}