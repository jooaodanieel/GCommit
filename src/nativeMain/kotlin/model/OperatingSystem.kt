package model

typealias ReturnedStatusCode = Int

interface OperatingSystem {
    fun getEnvValue(name: String): String?

    fun exec(command: String): ReturnedStatusCode
}
