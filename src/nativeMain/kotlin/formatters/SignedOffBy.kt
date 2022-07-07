package formatters

import model.Author
import model.SignatureFormatter

class SignedOffBy : SignatureFormatter {
    override fun generateSignatures(authors: List<Author>): String = authors
        .joinToString(separator = "\n") { (_, name, email) ->
            "Signed-off-by: $name <$email>"
        }
}