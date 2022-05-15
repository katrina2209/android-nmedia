package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.Post

class InMemoryPostRepository : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(10) { index ->
            Post(
                id = index + 1,
                author = "Ekaterina",
                content = "Some random content $index",
                published = "07.05.2022",
                likes = 32,
                shares = 0,
                viewings = 15_555
            )
        }
    )


    override fun like(postId: Int) {
        data.value = posts.map {
            if (it.id != postId) it else
                it.copy(
                    likedByMe = !it.likedByMe,
                    likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
                )
        }
    }

    override fun share(postId: Int) {
        data.value = posts.map {
            if (it.id != postId) it else
                it.copy(shares = it.shares + 1)
        }
    }
}