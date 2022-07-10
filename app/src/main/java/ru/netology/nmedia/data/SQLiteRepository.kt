package ru.netology.nmedia.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.netology.nmedia.db.PostDao

class SQLiteRepository(
    private val dao: PostDao
) : PostRepository {

    private val posts
        get() = checkNotNull(data.value) {
            "Data value should not be null"
        }

    override val data = MutableLiveData(dao.getAll())


    override fun save(post: Post) {
        val id = post.id
        val saved = dao.save(post)
        data.value = if (id == 0) {
            listOf(saved) + posts
        } else {
            posts.map {
                if (it.id != id) it else saved
            }
        }
    }

    override fun like(id: Int) {
        dao.likeById(id)
        data.value = posts.map {
            if (it.id != id) it else it.copy(
                likedByMe = !it.likedByMe,
                likes = if (it.likedByMe) it.likes - 1 else it.likes + 1
            )
        }
    }

    override fun share(id: Int) {
        dao.shareById(id)
        data.value = posts.map {
            if (it.id != id) it else
                it.copy(shares = it.shares + 1)
        }
    }

    override fun delete(id: Int) {
        dao.removeById(id)
        data.value = posts.filter { it.id != id }
    }
}

