package ru.netology.nmedia.service

import com.google.gson.annotations.SerializedName

class NewPost(
    @SerializedName("userId")
    val userId: Int,

    @SerializedName("userName")
    val userName: String,

    @SerializedName("postId")
    val postId: Int,

    @SerializedName("postText")
    val postText: String
) {
}