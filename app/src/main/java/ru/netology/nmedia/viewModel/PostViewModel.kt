package ru.netology.nmedia.viewModel

import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository

class PostViewModel : ViewModel() {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    fun onLikeClicked() = repository.like()

    fun onShareClicked () = repository.share()
}