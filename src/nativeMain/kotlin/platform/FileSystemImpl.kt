package platform

import model.ConfigNotFoundException
import model.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.use
import platform.posix.remove

class FileSystemImpl : FileSystem {
    override fun readFile(fileName: String): String {
        val filePath = fileName.toPath()

        try {
            var content = ""
            okio.FileSystem.SYSTEM.source(filePath).use { fs ->
                fs.buffer().use { buff ->
                    while (true) {
                        val line = buff.readUtf8Line() ?: break
                        content += line + "\n"
                    }
                }
            }

            return content
        } catch (_: okio.FileNotFoundException) {
            throw ConfigNotFoundException()
        }
    }

    override fun writeFile(fileName: String, content: String) {
        val filePath = fileName.toPath()

        okio.FileSystem.SYSTEM
            .sink(filePath)
            .buffer()
            .use { it.writeUtf8(content) }
    }

    override fun removeFile(fileName: String) {
        remove(fileName)
    }
}
