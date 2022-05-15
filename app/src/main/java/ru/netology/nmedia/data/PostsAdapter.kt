package ru.netology.nmedia.data

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ru.netology.nmedia.Post
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.PostBinding
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt

internal class PostsAdapter(

    private val onLikeClicked: (Post) -> Unit,
    private val onShareClicked: (Post) -> Unit,
) : ListAdapter<Post, PostsAdapter.ViewHolder>(DiffCallBack) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(
        private val binding: PostBinding
    ) : RecyclerView.ViewHolder(binding.root) {


        fun bind(post: Post) = with(binding) {
            author.text = post.author
            date.text = post.published
            content.text = post.content
            likesNumber.text = numberToString(post.likes)
            like.setImageResource(getLikeIconResId(post.likedByMe))
            sharesNumber.text = numberToString(post.shares)
            viewingsNumber.text = numberToString(post.viewings)
            like.setOnClickListener { onLikeClicked(post) }
            share.setOnClickListener { onShareClicked(post) }
        }

        @DrawableRes
        private fun getLikeIconResId(liked: Boolean) =
            if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_favorite_24dp


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


