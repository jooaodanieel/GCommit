package model

/**
 * The abstraction for the formatters
 *
 * Abstracts the strategy of generation of signatures
 */
interface SignatureFormatter {
    fun generateSignatures(authors: List<Author>): String
}

