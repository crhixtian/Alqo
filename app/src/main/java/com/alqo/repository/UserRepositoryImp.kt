package com.alqo.repository

import com.alqo.entities.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepositoryImp : UserRepository {
    override suspend fun obtainUser(id: String): User? {
        return try {
            val userDocument = FirebaseFirestore.getInstance()
                .collection("usuario")
                .document(id)
                .get()
                .await()

            User(
                names = userDocument.getString("nombres") ?: "",
                biography = userDocument.getString("biography") ?: "",
                create = userDocument.getString("create") ?: "",
                image = userDocument.getString("photo") ?: "",
                dni = userDocument.getString("dni") ?: ""
            )
        } catch (e: Exception) {
            null
        }
    }
}