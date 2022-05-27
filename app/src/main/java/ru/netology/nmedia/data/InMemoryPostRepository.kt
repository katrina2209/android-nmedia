package ru.netology.nmedia.data

import androidx.lifecycle.MutableLiveData

class InMemoryPostRepository : PostRepository {

    private var nextId = GENERATED_POSTS_AMOUNT
    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(
        List(GENERATED_POSTS_AMOUNT) { index ->
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

    override fun delete(postId: Int) {
        data.value = posts.filterNot { it.id == postId }

    }

    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) insert(post) else update(post)
    }

    private fun insert(post: Post) {
        data.value = listOf(post.copy(id = ++nextId)) + posts
    }

    private fun update(post: Post) {
        data.value = posts.map {
            if (it.id == post.id) post else it
        }
    }


    companion object {
        const val GENERATED_POSTS_AMOUNT = 1000
    }
}