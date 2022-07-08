import model.GCommit
import platform.FileSystemImpl
import platform.OperatingSystemImpl

fun main(args: Array<String>) = GCommit(
    fileSystem = FileSystemImpl(),
    operatingSystem = OperatingSystemImpl()
).main(args)
