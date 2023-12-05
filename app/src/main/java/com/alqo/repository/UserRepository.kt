package com.alqo.repository

import com.alqo.entities.User

interface UserRepository {
    suspend fun obtainUser(id: String): User?
}