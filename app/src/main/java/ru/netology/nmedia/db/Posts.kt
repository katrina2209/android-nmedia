package ru.netology.nmedia.db


import ru.netology.nmedia.data.Post

internal fun PostEntity.toModel() = Post(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    likedByMe = likedByMe,
    shares = shares,
    viewings = viewings,
    videoUrl = videoUrl
)

internal fun Post.toEntity() = PostEntity(
    id = id,
    author = author,
    content = content,
    published = published,
    likes = likes,
    likedByMe = likedByMe,
    shares = shares,
    viewings = viewings,
    videoUrl = videoUrl
)