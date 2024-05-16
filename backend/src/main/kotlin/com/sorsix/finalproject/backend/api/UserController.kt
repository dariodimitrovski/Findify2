package com.sorsix.finalproject.backend.api

import com.sorsix.finalproject.backend.authentication.service.HashService
import com.sorsix.finalproject.backend.domain.User
import com.sorsix.finalproject.backend.service.UserService
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@CrossOrigin
@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService, private val hashService: HashService) {
    @GetMapping()
    fun allUsers(): ResponseEntity<List<User>> {
        return ResponseEntity.ok(userService.listUsers())
    }

    @DeleteMapping("/delete/{id}")
    fun deleteUser(@PathVariable id: Long) = userService.deleteById(id)

//    @PutMapping("/users/{id}")
//    fun updateUser(
//        @PathVariable id: Long,
//        @RequestBody dto: RegisterDto
//    ): ResponseEntity<User> {
//        val user = userService.findById(id)
//        var pass = dto.password
//        pass = if (pass.isBlank())
//            user!!.password
//        else
//            hashService.hashBcrypt(pass)
//        userService.updateUserData(
//            id = id,
//            firstName = dto.firstName,
//            lastName = dto.lastName,
//            phoneNumber = dto.phoneNumber,
//            password = pass
//        )
//        return ResponseEntity.ok(userService.findById(id)!!)
//    }

    @PostMapping("/edit-profile")
    fun updateProfile(
        @RequestParam (required =  false) firstName: String?,
        @RequestParam  (required =  false)  lastName: String?,
        @RequestParam (required =  false)  password: String?,
        @RequestParam  (required =  false)  image: MultipartFile?,
        @RequestParam (required =  false) phoneNumber: String?
    ): ResponseEntity<User>{
        val response = userService.updateProfile(firstName, lastName, password, image, phoneNumber)
        return  ResponseEntity.ok().body(response)
    }

    @GetMapping("/image/{id}")
    fun getUserImage(@PathVariable id: Long): ResponseEntity<Any> {
//        val image: ByteArray = userService.getUserImage()
        val image: ByteArray = userService.findById(id)!!.image
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\\\"${System.currentTimeMillis()}\\\"")
            .body(image)
    }
}