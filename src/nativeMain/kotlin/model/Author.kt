package model

import kotlinx.serialization.Serializable

@Serializable
data class Author(
    val tag: String,
    val name: String,
    val email: String
)
