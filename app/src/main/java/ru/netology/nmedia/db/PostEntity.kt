package ru.netology.nmedia.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
class PostEntity(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "author")
    val author: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "published")
    val published: String,
    @ColumnInfo(name = "likes")
    val likes: Int = 0,
    @ColumnInfo(name = "shares")
    val shares: Int = 0,
    @ColumnInfo(name = "viewings")
    val viewings: Int = 0,
    @ColumnInfo(name = "likedByMe")
    val likedByMe: Boolean = false,
    @ColumnInfo(name = "videoUrl")
    val videoUrl: String? = null
)