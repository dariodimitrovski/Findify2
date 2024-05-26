package com.sorsix.finalproject.backend.domain

import jakarta.persistence.*
import org.hibernate.annotations.Type
import java.time.LocalDateTime
import kotlin.reflect.typeOf

@Entity
@Table(name = "posts")
data class Post(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    val id: Long = 1L,
    @Enumerated(EnumType.STRING)
    @Column
    val state: PostStatus = PostStatus.PENDING_LOST,
    @Column
    val title: String = "",
    @Column(columnDefinition = "BYTEA", name = "image")
    val image: ByteArray = ByteArray(1),
    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User(),
    @ManyToOne
    @JoinColumn(name = "municipality_id")
    val municipality: Municipality = Municipality(),
    @ManyToOne
    @JoinColumn(name = "category_id")
    val category: Category = Category(),
    @Column(name = "description")
    val description: String = "",
    @ManyToOne
    @JoinColumn(name = "location_id")
    val location: Location = Location(),
    @Column
    var time: String = "",
    @Transient
    var date: LocalDateTime = LocalDateTime.now()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Post

        if (!image.contentEquals(other.image)) return false

        return true
    }

    override fun hashCode(): Int {
        return image.contentHashCode()
    }

    fun setTime() {
        val currentTime = LocalDateTime.now()
        val daysDifference = currentTime.dayOfMonth.minus(LocalDateTime.parse(time).dayOfMonth.toLong())

        time = if (daysDifference < 1) {
            "Објавено денес"
        } else if (daysDifference == 1L) {
            "Објавено пред 1 ден"
        } else {
            "Објавено пред $daysDifference дена"
        }
    }
}

