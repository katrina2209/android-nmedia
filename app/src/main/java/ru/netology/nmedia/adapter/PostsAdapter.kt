package ru.netology.nmedia.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.data.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

internal class PostsAdapter(
    private val interactionListener: PostInteractionListener
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding, interactionListener)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(
        private val binding: PostBinding,
        listener: PostInteractionListener
    ) : RecyclerView.ViewHolder(binding.root) {

        private lateinit var post: Post

        private val popupMenu by lazy {
            PopupMenu(itemView.context, binding.options).apply {
                inflate(R.menu.options_post)
                setOnMenuItemClickListener { menuItem ->
                    when (menuItem.itemId) {
                        R.id.remove -> {
                            listener.onRemoveClicked(post)
                            true
                        }
                        R.id.edit -> {
                            listener.onEditClicked(post)
                            true
                        }
                        else -> false
                    }
                }
            }
        }

        init {
            binding.like.setOnClickListener {
                listener.onLikeClicked(post)
            }
            binding.share.setOnClickListener {
                listener.onShareClicked(post)
            }
            binding.options.setOnClickListener {
                popupMenu.show()
            }
            binding.buttonPlay.setOnClickListener {
                listener.onVideoClicked(post)
            }
            binding.video.setOnClickListener {
                listener.onVideoClicked(post)
            }
        }

        fun bind(post: Post) {

            this.post = post
            with(binding) {
                author.text = post.author
                date.text = post.published
                content.text = post.content
                like.text = numberToString(post.likes)
                like.isChecked = post.likedByMe
                share.text = numberToString(post.shares)
                viewing.text = numberToString(post.viewings)
                videoGroup.visibility =
                    if (post.videoUrl.isNullOrBlank()) View.GONE else View.VISIBLE
            }
        }

        private fun numberToString(number: Int): String {
            val df = DecimalFormat("#.#")
            df.roundingMode = RoundingMode.HALF_UP
            val num = number.toDouble()
            return when (number) {
                in 0..999 -> number.toString()
                in 1_000..9_999 -> "${df.format(num / 1000)}K"
                in 10_000..999_999 -> "${(num / 100).roundToInt() / 10}K"
                in 1_000_000..Integer.MAX_VALUE -> "${(num / 1_000_000).roundToInt()}M"
                else -> "ERR"
            }
        }
    }

    private object DiffCallBack : DiffUtil.ItemCallback<Post>() {
        override fun areItemsTheSame(oldItem: Post, newItem: Post) =
            oldItem.id == newItem.id


        override fun areContentsTheSame(oldItem: Post, newItem: Post) =
            oldItem == newItem
    }

}


