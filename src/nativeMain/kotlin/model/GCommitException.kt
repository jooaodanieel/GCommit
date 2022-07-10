package model

open class GCommitException(msg: String) : RuntimeException(
    """
    gcommit: $msg
    
    check your directory and run again
    
    """.trimIndent()
)
