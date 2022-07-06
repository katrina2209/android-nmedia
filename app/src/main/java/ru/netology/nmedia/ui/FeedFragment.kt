package ru.netology.nmedia.ui


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.*
import androidx.navigation.fragment.findNavController
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.databinding.FeedFragmentBinding
import ru.netology.nmedia.viewModel.PostViewModel


class FeedFragment : Fragment() {

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        viewModel.navigateToVideoWatching.observe(this) { videoUrl ->
            val intent = Intent()
                .apply {
                    action = Intent.ACTION_VIEW
                    data = Uri.parse(videoUrl)
                }
            val videoWatchingIntent =
                Intent.createChooser(intent, getString(R.string.chooser_watch_video))
            startActivity(videoWatchingIntent)
        }


        viewModel.sharePostContent.observe(this) { postContent ->
            val intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, postContent)
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(intent, getString(R.string.chooser_share_post))
            startActivity(shareIntent)
        }

        setFragmentResultListener(
            requestKey = PostContentFragment.REQUEST_KEY
        ) { requestKey, bundle ->
            if (requestKey != PostContentFragment.REQUEST_KEY) return@setFragmentResultListener
            val newPostContent =
                bundle.getString(PostContentFragment.RESULT_KEY) ?: return@setFragmentResultListener
            val newVideoUrl = bundle.getString("newVideoUrl")

            viewModel.onSaveButtonClicked(newPostContent, newVideoUrl)
        }

        viewModel.navigateToPostContentScreenEvent.observe(this) { editPostResult ->
            val direction = FeedFragmentDirections.toPostContentFragment(
                editPostResult?.newContent,
                editPostResult?.newVideoUrl
            )
            findNavController().navigate(direction)
        }


        viewModel.navigateToPostCardFragmentEvent.observe(this) { postId ->
            val direction = FeedFragmentDirections.actionFeedFragmentToPostCardFragment(postId)
            findNavController().navigate(direction)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FeedFragmentBinding.inflate(layoutInflater, container, false).also { binding ->

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddButtonClicked()
        }


    }.root

    companion object {
        const val TAG = "FeedFragment"
    }
}




