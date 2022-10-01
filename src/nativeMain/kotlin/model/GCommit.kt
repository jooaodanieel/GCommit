package model

import formatters.CoAuthoredBy
import formatters.SignedOffBy
import kotlinx.cinterop.refTo
import kotlinx.cinterop.toKString
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import platform.posix.fgets
import platform.posix.pclose
import platform.posix.popen

/**
 * The main class, the CLI tool
 *
 * This is the main class of GCommit, implements the entire tool's workflow
 *
 * @property fileSystem the object that provides access to file system operations
 * @property operatingSystem the object that provides access to O.S. operations
 */
class GCommit private constructor(
    private val fileSystem: FileSystem,
    private val operatingSystem: OperatingSystem
) {

    companion object {
        private const val CONFIG_FILE_PATH = "gcommit.conf.json"
        private const val COMMIT_TMP_FILE_NAME = "COMMIT_EDIT_MSG.tmp"
        private const val GIT_EDITOR_ENV_VAR = "GIT_EDITOR"
        private const val DEFAULT_EDITOR = "vi"
        private const val COMMIT_COMMAND = "git commit -m"
        private const val STATUS_COMMAND = "git status --porcelain"

        const val GITLAB_FORMAT_LABEL = "GCommit/GitLab"
        const val GITHUB_FORMAT_LABEL = "GCommit/GitHub"

        /**
         * The entrypoint for the execution
         *
         * This is a factory for the instanciation of the CLI tool
         *
         * @param fileSystem the object that provides access to file system operations
         * @param operatingSystem the object that provides access to O.S. operations
         * @param args the input from CLI execution arguments
         *
         * @see GCommitException to understand the importance of that being the mother-exception
         */
        fun main(fileSystem: FileSystem, operatingSystem: OperatingSystem, args: Array<String>) {
            try {
                GCommit(fileSystem, operatingSystem).run(args)
            } catch (gce: GCommitException) {
                println(gce.message)
            }
        }
    }

    private lateinit var config: Config
    private lateinit var formatter: SignatureFormatter
    private lateinit var editor: String

    init {
        loadConfig()
    }

    /**
     * Reads and applies the configuration
     *
     * Loading the configuration comprehends reading the different sources and applying it to the tool.
     * The editor is read from the environment GIT_EDITOR; the team and the formatter are read from the config file
     */
    private fun loadConfig() {
        config = fileSystem.readFile(CONFIG_FILE_PATH).let { parse(it) }
        editor = operatingSystem.getEnvValue(GIT_EDITOR_ENV_VAR) ?: DEFAULT_EDITOR
        formatter = when (config.format) {
            GITLAB_FORMAT_LABEL -> SignedOffBy()
            GITHUB_FORMAT_LABEL -> CoAuthoredBy()
            else -> CoAuthoredBy()
        }
    }

    /**
     * Parses the config file
     *
     * Parses the raw text from the config file into a useful Config object
     *
     * @param jsonContent the string read from the configuration file
     * @return a [Config] instance with the information
     * @throws MalformedConfigFileException
     */
    private fun parse(jsonContent: String): Config {
        try {
            return Json.decodeFromString(jsonContent)
        } catch (_: Exception) {
            throw MalformedConfigFileException()
        }
    }

    /**
     * Retrieves the status
     *
     * Retrieves the status of the work tree to understand if there are any changes to commit
     *
     * @return a [String] with the list of files that have been changed
     * @throws IllegalStateException
     */
    private fun retrieveStatus(): String {
        val fp = popen(STATUS_COMMAND, "r") ?: error("Failed to run command: $STATUS_COMMAND")

        val stdout = buildString {
            val buffer = ByteArray(4096)
            while (true) {
                val input = fgets(buffer.refTo(0), buffer.size, fp) ?: break
                append(input.toKString())
            }
        }

        pclose(fp)
        return stdout
    }

    /**
     * Generate the signatures from the tags
     *
     * Generate the signatures of the commit based on the chosen formatter and on the provided list of [Author] tags.
     * If no tag is provided, or no author is found, returns the signatures of all configured authors.
     *
     * @param tags the input from CLI execution arguments
     * @return a string with the formatted signatures
     */
    private fun createAuthorsSignatures(tags: Array<String>): String {
        return tags
            .mapNotNull { tag -> config[tag] }
            .let {
                val authors = it.ifEmpty { config.team }
                formatter.generateSignatures(authors)
            }
    }

    /**
     * Prepares the commit file with an initial message
     *
     * Creates a commit file with an initial message
     *
     * @param initialMessage the text to prepare the file
     */
    private fun prepareCommitFile(initialMessage: String) {
        val formattedMessage = "\n\n$initialMessage\n"
        fileSystem.writeFile(COMMIT_TMP_FILE_NAME, formattedMessage)
    }

    /**
     * Opens the commit file
     *
     * Automate the opening of the commit file for the user
     */
    private fun openCommitFile() = operatingSystem.exec(command = "$editor $COMMIT_TMP_FILE_NAME")

    /**
     * Actualize the commit
     *
     * Reads the commit message file and actualize the commit with the text
     */
    private fun commit() {
        val file = fileSystem.readFile(COMMIT_TMP_FILE_NAME)
        operatingSystem.exec("$COMMIT_COMMAND \"$file\"")
    }

    /**
     * Clears the commit file
     *
     * Clears the temporary commit message file to avoid trash in future executions
     */
    private fun clearTmpFile() = fileSystem.removeFile(COMMIT_TMP_FILE_NAME)

    /**
     * Runs the tool
     *
     * Implements the execution's workflow of the tool based of the CLI arguments
     *
     * @param args the input from CLI execution arguments
     */
    private fun run(args: Array<String>) {
        val status = retrieveStatus()
        if (status.isEmpty()) throw GCommitException("nothing to commit, working tree clean")

        val commitMsgTail = createAuthorsSignatures(args)

        prepareCommitFile(commitMsgTail)
        openCommitFile()
        commit()
        clearTmpFile()
    }
}
