package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository : PostRepository {
    override val data = MutableLiveData(
        Post(
            id = 153642,
            author = "Ekaterina",
            content = "Hello everybody!",
            published = "03.05.2022",
            likes = 32,
            shares = 0,
            viewings = 15_555_555
        )
    )


    override fun like() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val likedPost = currentPost.copy(
            likedByMe = !currentPost.likedByMe,
            likes = if (currentPost.likedByMe) currentPost.likes - 1 else currentPost.likes + 1
        )
        data.value = likedPost
    }

    override fun share() {
        val currentPost = checkNotNull(data.value) {
            "Data value should not be null"
        }
        val sharedPost = currentPost.copy(
            shares = currentPost.shares + 1
        )
        data.value = sharedPost
    }

}