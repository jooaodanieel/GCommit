package model

/**
 * The main exception
 *
 * This exception is meant to be the mother-class of all following domain exceptions
 *
 * @param msg the explanatory error message to be displayed to the user
 * @see GCommit
 */
open class GCommitException(msg: String) : RuntimeException(
    """
    gcommit: $msg
    
    check your directory and run again
    
    """.trimIndent()
)
