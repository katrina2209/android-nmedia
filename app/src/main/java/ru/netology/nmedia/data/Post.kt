package ru.netology.nmedia.data

import kotlinx.serialization.Serializable

@Serializable
data class Post(
    val id: Int,
    val author: String,
    val content: String,
    val published: String,
    val likes: Int = 0,
    val shares: Int = 0,
    val viewings: Int = 0,
    val likedByMe: Boolean = false,
    val videoUrl: String? = null
)

// Класс для передачи из контракта результата работы активити
class EditPostResult(
    val newContent: String?,
    val newVideoUrl: String?
)

