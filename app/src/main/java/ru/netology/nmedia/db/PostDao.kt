package ru.netology.nmedia.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY id DESC")
    fun getAll(): LiveData<List<PostEntity>>

    @Insert
    fun insert(post: PostEntity)

    @Query(
        """
        UPDATE posts SET 
        content = :content,
        videoUrl = :videoUrl
        WHERE id = :id
        """
    )
    fun updateContentById(id: Int, content: String, videoUrl: String)


    @Query(
        """
        UPDATE posts SET
        likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
        likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
        WHERE id = :id
        """
    )
    fun likeById(id: Int)

    @Query("DELETE FROM posts WHERE id = :id")
    fun removeById(id: Int)

    @Query("UPDATE posts SET shares = shares + 1 WHERE id = :id")
    fun shareById(id: Int)

}