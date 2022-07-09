package model

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class ConfigTest {
    @Test
    fun `deserialization happy path`() {
        // given
        val configString = twoMembersNoFormat()

        // when
        val result = Json.decodeFromString<Config>(configString)

        // then
        assertIs<Config>(result)
    }

    @Test
    fun `default format is GitHubs`() {
        // given
        val configString = twoMembersNoFormat()

        // when
        val config = Json.decodeFromString<Config>(configString)

        // then
        assertEquals(GCommit.GITHUB_FORMAT_LABEL, config.format)
    }

    @Test
    fun `format can be overridden to GitLabs`() {
        // given
        val configString: String = twoMembersFormatGitlab()

        // when
        val config = Json.decodeFromString<Config>(configString)

        // then
        assertEquals(GCommit.GITLAB_FORMAT_LABEL, config.format)
    }

    private fun twoMembersFormatGitlab(): String = """
        {
            "team": [
                {"tag": "AB", "name": "Alf Bulling", "email": "alf.b@mail.com" },
                {"tag": "BC", "name": "Beth Chang", "email": "b.chang@mail.com" }
            ],
            "format": "GCommit/GitLab"
        }
    """.trimIndent()

    private fun twoMembersNoFormat(): String = """
        {
            "team": [
                {"tag": "AB", "name": "Alf Bulling", "email": "alf.b@mail.com" },
                {"tag": "BC", "name": "Beth Chang", "email": "b.chang@mail.com" }
            ]
        }
    """.trimIndent()
}
