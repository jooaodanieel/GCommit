package model

import kotlinx.serialization.Serializable

@Serializable
data class Config(
    val team: List<Author>,
    val format: String = GCommit.GITHUB_FORMAT_LABEL
) {
    operator fun get(index: String): Author? = team.find { it.tag == index }
}
