import kotlinx.cinterop.toKString
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use
import platform.posix.getenv
import platform.posix.remove
import platform.posix.system

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
    private lateinit var commitMsgPath: Path

    init {
        loadConfig()
    }

    private fun loadConfig() {
        val configFilePath = "gcommit.conf.json".toPath()
        config = readFile(configFilePath).let { parse(it) }
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

    private fun prepareCommitFile(initialMessage: String) = FileSystem.SYSTEM
        .sink(commitMsgPath)
        .buffer()
        .use { sink ->
            sink.writeUtf8("\n")
            sink.writeUtf8("\n")
            sink.writeUtf8(initialMessage)
            sink.writeUtf8("\n")
        }

    private fun openCommitFile() = system("$editor $commitMsgPath")

    private fun readFile(filePath: Path): String {
        var content = ""
        FileSystem.SYSTEM.source(filePath).use { fs ->
            fs.buffer().use { buff ->
                while (true) {
                    val line = buff.readUtf8Line() ?: break
                    content += line + "\n"
                }
            }
        }

        return content
    }

    private fun commit() {
        val file = readFile(commitMsgPath)
        system("git commit -m \"$file\"")
    }

    private fun clearTmpFile() = remove(commitMsgPath.toString())

    fun main(args: Array<String>) {
        val commitMsgTail = createAuthorsSignatures(args)
        commitMsgPath = "COMMIT_EDIT_MSG.tmp".toPath()
        prepareCommitFile(commitMsgTail)
        openCommitFile()
        commit()
        clearTmpFile()
    }
}

fun main(args: Array<String>) = GCommit().main(args)
