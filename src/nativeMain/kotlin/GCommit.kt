import formatters.CoAuthoredBy
import formatters.SignedOffBy
import kotlinx.cinterop.toKString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import model.Config
import model.SignatureFormatter
import okio.FileSystem
import okio.Path
import okio.Path.Companion.toPath
import okio.buffer
import okio.use
import platform.posix.getenv
import platform.posix.remove
import platform.posix.system

class GCommit {

    companion object {
        private const val CONFIG_FILE_PATH = "gcommit.conf.json"
        private const val COMMIT_TMP_FILE_NAME = "COMMIT_EDIT_MSG.tmp"
        private const val GIT_EDITOR_ENV_VAR = "GIT_EDITOR"
        private const val DEFAULT_EDITOR = "vi"
        private const val COMMIT_COMMAND = "git commit -m"

        const val GITLAB_FORMAT_LABEL = "GCommit/GitLab"
        const val GITHUB_FORMAT_LABEL = "GCommit/GitHub"
    }

    private lateinit var config: Config
    private lateinit var formatter: SignatureFormatter
    private lateinit var editor: String
    private lateinit var commitMsgPath: Path

    init {
        loadConfig()
    }

    private fun loadConfig() {
        val configFilePath = CONFIG_FILE_PATH.toPath()

        commitMsgPath = COMMIT_TMP_FILE_NAME.toPath()
        config = readFile(configFilePath).let { parse(it) }
        editor = getenv(GIT_EDITOR_ENV_VAR)?.toKString() ?: DEFAULT_EDITOR
        formatter = when (config.format) {
            GITLAB_FORMAT_LABEL -> SignedOffBy()
            GITHUB_FORMAT_LABEL -> CoAuthoredBy()
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
        system("$COMMIT_COMMAND \"$file\"")
    }

    private fun clearTmpFile() = remove(commitMsgPath.toString())

    fun main(args: Array<String>) {
        val commitMsgTail = createAuthorsSignatures(args)

        prepareCommitFile(commitMsgTail)
        openCommitFile()
        commit()
        clearTmpFile()
    }
}