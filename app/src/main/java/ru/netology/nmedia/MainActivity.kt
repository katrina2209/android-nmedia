package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import ru.netology.nmedia.viewModel.PostViewModel
import java.math.RoundingMode
import java.text.DecimalFormat
import kotlin.math.roundToInt


class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<PostViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        viewModel.data.observe(this) { post ->
            binding.render(post)
        }

        binding.like.setOnClickListener {
            viewModel.onLikeClicked()
        }

        binding.share.setOnClickListener {
            viewModel.onShareClicked()
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        date.text = post.published
        content.text = post.content
        likesNumber.text = numberToString(post.likes)
        like.setImageResource(getLikeIconResId(post.likedByMe))
        sharesNumber.text = numberToString(post.shares)
        viewingsNumber.text = numberToString(post.viewings)

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



