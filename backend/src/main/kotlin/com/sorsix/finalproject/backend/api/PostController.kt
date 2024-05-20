package com.sorsix.finalproject.backend.api

import com.sorsix.finalproject.backend.domain.Location
import com.sorsix.finalproject.backend.domain.Post
import com.sorsix.finalproject.backend.domain.PostStatus
import com.sorsix.finalproject.backend.domain.User
import com.sorsix.finalproject.backend.service.*
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime


@RestController
@CrossOrigin
@RequestMapping("/api/posts")
class PostController(
    private val postService: PostService,
    private val categoryService: CategoryService,
    private val municipalityService: MunicipalityService,
    private val locationService: LocationService,
    private val userService: UserService
) {

    @GetMapping("/lost-items")
    fun getLostItems(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Post>> {

        val pageNum = page ?: 0
        val sizeNum = size ?: 3

        return ResponseEntity.ok(postService.findByStatus(PostStatus.ACTIVE_LOST, pageNum, sizeNum).content)
    }

    @GetMapping("/lost-items-size")
    fun getLostItemsSize(): Long{
        return this.postService.findByStatus(PostStatus.ACTIVE_LOST, 0, 1).totalElements
    }

    @GetMapping("/found-items")
    fun getFoundItems(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Post>> {

        val pageNum = page ?: 0
        val sizeNum = size ?: 3

        return ResponseEntity.ok(postService.findByStatus(PostStatus.ACTIVE_FOUND, pageNum, sizeNum).content)
    }
    @GetMapping("/found-items-size")
    fun getFoundItemsSize(): Long{
        return this.postService.findByStatus(PostStatus.ACTIVE_FOUND, 0, 1).totalElements
    }

    @GetMapping("/pending-items")
    fun getPendingItems(
        @RequestParam(required = false) page: Int?,
        @RequestParam(required = false) size: Int?
    ): ResponseEntity<List<Post>> {

        val pageNum = page ?: 0
        val sizeNum = size ?: 10

        return ResponseEntity.ok(postService.findByStatus(PostStatus.PENDING_LOST, pageNum, sizeNum).content + postService.findByStatus(PostStatus.PENDING_FOUND, pageNum, sizeNum).content)
    }

    @GetMapping("/pending-items-size")
    fun getPendingItemsSize(): Long{
        return this.postService.findByStatus(PostStatus.PENDING_LOST, 0, 1).totalElements + this.postService.findByStatus(PostStatus.PENDING_FOUND, 0, 1).totalElements
    }

    @GetMapping("/{id}")
    fun getPost(@PathVariable id: Long): ResponseEntity<Post> {
        return postService.findById(id)?.let {
            ResponseEntity.ok(it)
        } ?: ResponseEntity.notFound().build()
    }

    @GetMapping("/user/{id}")
    fun getPostsByUserId(@PathVariable id: Long): ResponseEntity<List<Post>> {
        return postService.listAll()
            .stream()
            .filter { post -> post.user.id == id }
            .toList()
            .let { ResponseEntity.ok(it) }
    }

    @PutMapping("/update/{id}")
    fun updatePost(@PathVariable id: Long): ResponseEntity<Post> {
        val post = postService.findById(id)
        val postStatus = if (post?.state == PostStatus.PENDING_LOST) {
            PostStatus.ACTIVE_LOST
        } else {
            PostStatus.ACTIVE_FOUND
        }
        postService.updateState(id, postStatus)
        return ResponseEntity.ok(post)
    }

    @DeleteMapping("/delete/{id}")
    fun deletePost(@PathVariable id: Long) = postService.deleteById(id)


    @PostMapping("/add/new-post")
    fun addPost(
        @RequestParam title: String,
        @RequestParam category: String,
        @RequestParam description: String,
        @RequestParam municipality: String,
        @RequestParam image: MultipartFile,
        @RequestParam state: String,
        @RequestParam userId: Long,
        @RequestParam(required = false) lng: Double,
        @RequestParam(required = false) lat: Double,
        @RequestParam(required = true) userId: Long
    ): ResponseEntity<Post> {
        val cat = categoryService.findCategoryByName(category)
        val mun = municipalityService.findMunicipalityByName(municipality)
        val s: PostStatus = PostStatus.valueOf(state)
        locationService.save(Location(1L, lng, lat))
        val loc = locationService.findLatest()!!

        val post = postService.create(
            title = title,
            category = cat!!,
            description = description,
            municipality = mun!!,
            image = image,
            status = s,
            user = userService.findById(userId)!!,
            location = loc,
            time = LocalDateTime.now().toString(),
        )
        return ResponseEntity.ok().body(post)
    }

    @PostMapping("/filter")
    fun filterPosts(
        @RequestParam(required = false) title: String,
        @RequestParam(required = false) category: String,
        @RequestParam(required = false) municipality: String,
        @RequestParam(required = true) state: String,
        @RequestParam(required = false) order: String
    ): ResponseEntity<List<Post>> {

        val cat = categoryService.findCategoryByName(category)
        val mun = municipalityService.findMunicipalityByName(municipality)
        val s: PostStatus = if (state == "ACTIVE_LOST")
            PostStatus.ACTIVE_LOST
        else
            PostStatus.ACTIVE_FOUND

        val posts: List<Post> = postService.filter(title, cat, mun, s, order)

        return ResponseEntity.ok().body(posts)

    }

    @GetMapping("/{postId}/image")
    fun getPostImage(@PathVariable postId: Long): ResponseEntity<Any> {
        val image: ByteArray = postService.getPostImage(postId)

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(MediaType.IMAGE_PNG_VALUE))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\\\"${System.currentTimeMillis()}\\\"")
            .body(image)
    }

}