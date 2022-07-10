import model.GCommit
import platform.FileSystemImpl
import platform.OperatingSystemImpl

fun main(args: Array<String>) = GCommit.main(
    fileSystem = FileSystemImpl(),
    operatingSystem = OperatingSystemImpl(),
    args
)
