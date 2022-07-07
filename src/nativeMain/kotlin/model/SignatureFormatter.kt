package model

interface SignatureFormatter {
    fun generateSignatures(authors: List<Author>): String
}