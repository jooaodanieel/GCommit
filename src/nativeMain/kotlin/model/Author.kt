package model

import kotlinx.serialization.Serializable

/**
 * An author member of the development team
 *
 * This class represents the structure for the data in the configuration file regarding one author
 *
 * @property tag the identification to reference this author
 * @property name the name to be displayed in the commit message
 * @property email the email adopted in the Code Repository platform
 */
@Serializable
data class Author(
    val tag: String,
    val name: String,
    val email: String
)
