package platform

import kotlinx.cinterop.toKString
import model.OperatingSystem
import model.ReturnedStatusCode
import platform.posix.getenv
import platform.posix.system

class OperatingSystemImpl : OperatingSystem {
    override fun getEnvValue(name: String): String? = getenv(name)?.toKString()

    override fun exec(command: String): ReturnedStatusCode = system(command)
}
