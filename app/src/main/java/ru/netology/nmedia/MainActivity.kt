package ru.netology.nmedia


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import ru.netology.nmedia.adapter.PostsAdapter
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.util.focusAndShowKeyboard
import ru.netology.nmedia.util.hideKeyboard
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
        binding.saveButton.setOnClickListener {
            with(binding.contentEditText) {
                val content = text.toString()
                viewModel.onSaveButtonClicked(content)
                binding.groupEditMessage.visibility = View.GONE
            }
        }
        binding.cancelEdit.setOnClickListener {
            viewModel.onCancelButtonClicked()
            with(binding) {
                groupEditMessage.visibility = View.GONE
                contentEditText.clearFocus()
                contentEditText.hideKeyboard()
            }
        }



        viewModel.currentPost.observe(this) { currentPost ->
            with(binding.contentEditText) {
                val content = currentPost?.content
                setText(content)
                if (content != null) {
                    setSelection(text.length)
                    requestFocus()
                    focusAndShowKeyboard()
                    binding.groupEditMessage.visibility = View.VISIBLE
                } else {
                    clearFocus()
                    hideKeyboard()
                    binding.groupEditMessage.visibility = View.GONE
                }
            }
        }
    }
}


