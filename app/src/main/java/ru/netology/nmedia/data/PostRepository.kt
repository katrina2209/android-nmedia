package ru.netology.nmedia.data

import androidx.lifecycle.LiveData

interface PostRepository {

    val data: LiveData<List<Post>>

    fun like(postId: Int)

    fun share(postId: Int)

    fun delete(postId: Int)

    fun save(post: Post)

    fun edit (post: Post)


    companion object {
        const val NEW_POST_ID = 0
    }
}