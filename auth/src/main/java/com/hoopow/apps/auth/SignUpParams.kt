package com.hoopow.apps.auth

data class SignUpParams(
    val email: String,
    val password: String,
    val displayName: String
)
