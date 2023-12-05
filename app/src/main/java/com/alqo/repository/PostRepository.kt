package com.alqo.repository

import com.alqo.entities.Post

interface PostRepository {
    suspend fun obtainPost(): MutableList<Post>
}