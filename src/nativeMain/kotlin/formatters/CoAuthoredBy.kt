package formatters

import model.Author
import model.SignatureFormatter

class CoAuthoredBy : SignatureFormatter {
    override fun generateSignatures(authors: List<Author>): String = authors
        .joinToString(separator = "\n") { (_, name, email) ->
            "Co-authored-by: $name <$email>"
        }
}