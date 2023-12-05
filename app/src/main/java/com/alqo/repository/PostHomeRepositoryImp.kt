package com.alqo.repository

import android.util.Log
import com.alqo.entities.Post
import com.alqo.utils.Time
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class PostHomeRepositoryImp : PostRepository {
    override suspend fun obtainPost(): MutableList<Post> {
        val listPost = mutableListOf<Post>()

        try {
            for (document in FirebaseFirestore.getInstance()
                .collection("post")
                .orderBy("create", Query.Direction.DESCENDING)
                .get()
                .await()
                .documents) {
                val postTime = document.getTimestamp("create")?.toDate()

                listPost.add(
                    Post(
                        names = document.getString("nombres") ?: "",
                        create = postTime?.let { Time.timeAgo(it) } ?: "",
                        content = document.getString("content") ?: "",
                        image = document.getString("image") ?: "",
                        profile = document.getString("profile") ?: "",
                        uid = document.getString("uid") ?: ""
                    )
                )
            }
        } catch (e: Exception) {
            Log.e("FirestoreQuery", "Error: $e")
        }
        return listPost
    }
}