package model

import formatters.CoAuthoredBy
import formatters.SignedOffBy
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class GCommit(
    private val fileSystem: FileSystem,
    private val operatingSystem: OperatingSystem
) {

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

    init {
        loadConfig()
    }

    private fun loadConfig() {
        config = fileSystem.readFile(CONFIG_FILE_PATH).let { parse(it) }
        editor = operatingSystem.getEnvValue(GIT_EDITOR_ENV_VAR) ?: DEFAULT_EDITOR
        formatter = when (config.format) {
            GITLAB_FORMAT_LABEL -> SignedOffBy()
            GITHUB_FORMAT_LABEL -> CoAuthoredBy()
            else -> CoAuthoredBy()
        }
    }

    private fun parse(jsonContent: String): Config = Json.decodeFromString(jsonContent)

    private fun createAuthorsSignatures(tags: Array<String>): String {
        return tags
            .mapNotNull { tag -> config[tag] }
            .let {
                val authors = it.ifEmpty { config.team }
                formatter.generateSignatures(authors)
            }
    }

    private fun prepareCommitFile(initialMessage: String) {
        val formattedMessage = "\n\n$initialMessage\n"
        fileSystem.writeFile(COMMIT_TMP_FILE_NAME, formattedMessage)
    }

    private fun openCommitFile() = operatingSystem.exec(command = "$editor $COMMIT_TMP_FILE_NAME")

    private fun commit() {
        val file = fileSystem.readFile(COMMIT_TMP_FILE_NAME)
        operatingSystem.exec("$COMMIT_COMMAND \"$file\"")
    }

    private fun clearTmpFile() = fileSystem.removeFile(COMMIT_TMP_FILE_NAME)

    fun main(args: Array<String>) {
        val commitMsgTail = createAuthorsSignatures(args)

        prepareCommitFile(commitMsgTail)
        openCommitFile()
        commit()
        clearTmpFile()
    }
}
