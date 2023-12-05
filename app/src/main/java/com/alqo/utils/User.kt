package com.alqo.utils

import com.google.firebase.auth.FirebaseAuth

object User {
    fun uid(): String {
        return FirebaseAuth.getInstance().currentUser?.uid!!.toString()
    }
}