package ru.netology.nmedia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.DrawableRes
import ru.netology.nmedia.databinding.ActivityMainBinding
import java.math.RoundingMode
import java.text.DecimalFormat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val post = Post(
            id = 153642,
            author = "Ekaterina",
            content = "Hello everybody!",
            published = "03.05.2022",
            likes = 999,
            shares = 0,
            viewings = 15_555_555
        )

        binding.render(post)
        binding.like.setOnClickListener {
            post.likedByMe = !post.likedByMe
            binding.like.setImageResource(getLikeIconResId(post.likedByMe))
            binding.likesNumber.text = numberToString(getLikesCount(post))

        }

        binding.share.setOnClickListener {
            binding.sharesNumber.text = numberToString(post.shares++)
        }
    }

    private fun ActivityMainBinding.render(post: Post) {
        author.text = post.author
        date.text = post.published
        content.text = post.content
        likesNumber.text = numberToString(getLikesCount(post))
        like.setImageResource(getLikeIconResId(post.likedByMe))
        sharesNumber.text = numberToString(post.shares++)
        viewingsNumber.text = numberToString(post.viewings)

    }

    @DrawableRes
    private fun getLikeIconResId(liked: Boolean) =
        if (liked) R.drawable.ic_liked_24dp else R.drawable.ic_favorite_24dp

    private fun getLikesCount(post: Post) =
        if (post.likedByMe) post.likes-- else post.likes++

    private fun numberToString(number: Int): String {
        val df = DecimalFormat("#.#")
        df.roundingMode = RoundingMode.HALF_UP
        val num = number.toDouble()
        return when (number) {
            in 0..999 -> number.toString()
            in 1_000..9_999 -> "${df.format(num / 1000)}K"
            in 10_000..999_999 -> "${Math.round(num / 100) / 10}K"
            in 1_000_000..Integer.MAX_VALUE -> "${Math.round(num / 1_000_000)}M"
            else -> "ERR"
        }

    }

}



