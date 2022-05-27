package ru.netology.nmedia.activity


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import ru.netology.nmedia.R
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PostsAdapter(viewModel)
        binding.postsRecyclerView.adapter = adapter
        viewModel.data.observe(this) { posts ->
            adapter.submitList(posts)
        }
        binding.fab.setOnClickListener {
            viewModel.onAddButtonClicked()
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
        val postContentActivityLauncher =
            registerForActivityResult(PostContentActivity.ResultContract) { postContent ->
                postContent ?: return@registerForActivityResult
                viewModel.onSaveButtonClicked(postContent)
            }
        viewModel.navigateToPostContentScreenEvent.observe(this) {
            postContentActivityLauncher.launch(it)
        }
    }
}


//        binding.cancelEdit.setOnClickListener {
//            viewModel.onCancelButtonClicked()
//            with(binding) {
//                groupEditMessage.visibility = View.GONE
//                contentEditText.clearFocus()
//                contentEditText.hideKeyboard()
//            }
//        }


//        viewModel.currentPost.observe(this) { currentPost ->
//            with(binding.contentEditText) {
//                val content = currentPost?.content
//                setText(content)
//                if (content != null) {
//                    setSelection(text.length)
//                    requestFocus()
//                    focusAndShowKeyboard()
//                    //binding.groupEditMessage.visibility = View.VISIBLE
//                } else {
//                    clearFocus()
//                    hideKeyboard()
//                    //binding.groupEditMessage.visibility = View.GONE
//                }
//            }
//        }



