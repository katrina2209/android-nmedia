package ru.netology.nmedia.data


import androidx.lifecycle.map
import ru.netology.nmedia.db.PostDao
import ru.netology.nmedia.db.toEntity
import ru.netology.nmedia.db.toModel

class PostRepositoryImpl(
    private val dao: PostDao
) : PostRepository {

    override val data = dao.getAll().map { entities ->
        entities.map { it.toModel() }
    }


    override fun save(post: Post) {
        if (post.id == PostRepository.NEW_POST_ID) dao.insert(post.toEntity()) else post.videoUrl?.let {
            dao.updateContentById(
                post.id, post.content,
                it
            )
        }
    }

    override fun like(postId: Int) {
        dao.likeById(postId)
    }

    override fun share(postId: Int) {
        dao.shareById(postId)
    }

    override fun delete(postId: Int) {
        dao.removeById(postId)
    }

    override fun edit(post: Post) {
        post.videoUrl?.let { dao.updateContentById(post.id, post.content, it) }
    }
}

