import kotlinx.cinterop.toKString
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import platform.posix.getenv

private fun readConfFile() = """
{
  "team": [
    {
      "tag": "ABC",
      "name": "Albert B. Cheez",
      "email": "alb.cheez@mail.com"
    },
    {
      "tag": "DEF",
      "name": "Donald E. Fitzgerald",
      "email": "fitz.done@mail.com"
    }
  ]
}
"""

@Serializable
data class Author(
    val tag: String,
    val name: String,
    val email: String
)

@Serializable
data class Config(
    val team: List<Author>,
    val format: String = "GCommit/GitHub"
) {
    operator fun get(index: String): Author? = team.find { it.tag == index }
}

interface SignatureFormatter {
    fun generateSignatures(authors: List<Author>): String
}

class CoAuthoredBy : SignatureFormatter {
    override fun generateSignatures(authors: List<Author>): String = authors
        .joinToString(separator = "\n") { (_, name, email) ->
            "Co-authored-by: $name <$email>"
        }
}

class SignedOffBy : SignatureFormatter {
    override fun generateSignatures(authors: List<Author>): String = authors
        .joinToString(separator = "\n") { (_, name, email) ->
            "Signed-off-by: $name <$email>"
        }
}

class GCommit {

    private lateinit var config: Config
    private lateinit var formatter: SignatureFormatter
    private lateinit var editor: String

    init {
        loadConfig()
    }

    private fun loadConfig() {
        config = readConfFile().let { parse(it) }
        editor = getenv("GIT_EDITOR")?.toKString() ?: "vi"
        formatter = when (config.format) {
            "GCommit/GitLab" -> SignedOffBy()
            "GCommit/GitHub" -> CoAuthoredBy()
            else -> CoAuthoredBy()
        }
    }

    private fun parse(file: String): Config = Json.decodeFromString(file)

    private fun createAuthorsSignatures(tags: Array<String>): String {
        return tags
            .mapNotNull { tag -> config[tag] }
            .let {
                val authors = it.ifEmpty { config.team }
                formatter.generateSignatures(authors)
            }
    }

    fun main(args: Array<String>) {
        val tail = createAuthorsSignatures(args)

        println(tail)
    }
}

fun main(args: Array<String>) = GCommit().main(args)
