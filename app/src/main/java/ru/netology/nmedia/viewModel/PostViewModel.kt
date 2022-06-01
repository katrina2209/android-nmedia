package ru.netology.nmedia.viewModel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.adapter.PostInteractionListener
import ru.netology.nmedia.data.EditPostResult
import ru.netology.nmedia.data.InMemoryPostRepository
import ru.netology.nmedia.data.PostRepository
import ru.netology.nmedia.util.SingleLiveEvent

class PostViewModel : ViewModel(), PostInteractionListener {

    private val repository: PostRepository = InMemoryPostRepository()

    val data get() = repository.data

    val sharePostContent = SingleLiveEvent<String>()
    val navigateToPostContentScreenEvent = SingleLiveEvent<EditPostResult?>()
    val navigateToVideoWatching = SingleLiveEvent<String?>()

    private val currentPost = MutableLiveData<Post?>(null)


    fun onSaveButtonClicked(content: String, videoUrl: String?) {

        if (content.isBlank()) return
        val post = currentPost.value?.copy(
            content = content,
            videoUrl = videoUrl
        ) ?: Post(
            id = PostRepository.NEW_POST_ID,
            author = "Me",
            content = content,
            published = "Today",
            videoUrl = videoUrl
        )
        repository.save(post)
        currentPost.value = null
    }

    fun onAddButtonClicked() {
        navigateToPostContentScreenEvent.call()
    }


    // region PostInteractionListener

    override fun onLikeClicked(post: Post) = repository.like(post.id)

    override fun onShareClicked(post: Post) {
        sharePostContent.value = post.content
        repository.share(post.id)
    }

    override fun onRemoveClicked(post: Post) {
        currentPost.value = null
        repository.delete(post.id)
    }

    override fun onEditClicked(post: Post) {
        currentPost.value = post
        navigateToPostContentScreenEvent.value = EditPostResult(post.content, post.videoUrl)
    }

    override fun onVideoClicked(post: Post) {
        navigateToVideoWatching.value = post.videoUrl
    }


    // endregion PostInteractionListener
}
