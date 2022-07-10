package ru.netology.nmedia.db

import android.database.Cursor
import ru.netology.nmedia.data.Post

fun Cursor.toPost() = Post(
    id = getInt(getColumnIndexOrThrow(PostsTable.Column.ID.columnName)),
    author = getString(getColumnIndexOrThrow(PostsTable.Column.AUTHOR.columnName)),
    content = getString(getColumnIndexOrThrow(PostsTable.Column.CONTENT.columnName)),
    published = getString(getColumnIndexOrThrow(PostsTable.Column.PUBLISHED.columnName)),
    likes = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKES.columnName)),
    likedByMe = getInt(getColumnIndexOrThrow(PostsTable.Column.LIKED_BY_ME.columnName)) != 0,
    shares = getInt(getColumnIndexOrThrow(PostsTable.Column.SHARES.columnName)),
    viewings = getInt(getColumnIndexOrThrow(PostsTable.Column.VIEWINGS.columnName)),
    videoUrl = getString(getColumnIndexOrThrow(PostsTable.Column.VIDEO_URL.columnName))
)