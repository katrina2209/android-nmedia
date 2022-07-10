package ru.netology.nmedia.db

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import ru.netology.nmedia.data.Post

class PostDaoImpl(
    private val db: SQLiteDatabase
) : PostDao {

    override fun getAll() = db.query(
        PostsTable.NAME,
        PostsTable.ALL_COLUMNS_NAMES,
        null, null, null, null,
        "${PostsTable.Column.ID.columnName} DESC"
    ).use { cursor ->
        List(cursor.count) {
            cursor.moveToNext()
            cursor.toPost()
        }
    }

    override fun save(post: Post): Post {
        val values = ContentValues().apply {
            put(PostsTable.Column.AUTHOR.columnName, post.author)
            put(PostsTable.Column.CONTENT.columnName, post.content)
            put(PostsTable.Column.PUBLISHED.columnName, post.published)
            put(PostsTable.Column.VIDEO_URL.columnName, post.videoUrl)
        }
        val id = if (post.id != 0) {
            db.update(
                PostsTable.NAME,
                values,
                "${PostsTable.Column.ID.columnName} = ?",
                arrayOf(post.id.toString())
            )
            post.id
        } else { //post.id == 0
            db.insert(PostsTable.NAME, null, values)
        }

        return db.query(
            PostsTable.NAME,
            PostsTable.ALL_COLUMNS_NAMES,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString()),
            null, null, null,null
        ).use { cursor ->
            cursor.moveToNext()
            cursor.toPost()
        }
    }


    override fun likeById(id: Int) {
        db.execSQL(
            """
                UPDATE ${PostsTable.NAME} SET
                likes = likes + CASE WHEN likedByMe THEN -1 ELSE 1 END,
                likedByMe = CASE WHEN likedByMe THEN 0 ELSE 1 END
                WHERE id = ?;
            """.trimIndent(),
            arrayOf(id)
        )
    }
    override fun shareById(id: Int) {
        db.execSQL(
            """
                UPDATE ${PostsTable.NAME} SET
                shares = shares + 1
                WHERE id = ?;
            """.trimIndent(),
            arrayOf(id)
        )
    }


    override fun removeById(id: Int) {
        db.delete(
            PostsTable.NAME,
            "${PostsTable.Column.ID.columnName} = ?",
            arrayOf(id.toString())
        )
    }


}