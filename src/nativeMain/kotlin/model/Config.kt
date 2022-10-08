package model

import kotlinx.serialization.Serializable

/**
 * The configuration for GCommit
 *
 * This class represents the structure for the all the data configurable via gcommit.conf.json
 *
 * @property team a list of [Author]
 * @property format [default: GCommit/GitHub] signing format adopted by the Code Repository platform
 * @property namedTeams [default: null] the map of namedTeams with relative tags
 */
@Serializable
data class Config(
    val team: List<Author>,
    val format: String = GCommit.GITHUB_FORMAT_LABEL,
    val namedTeams: NamedTeams? = null
) {

    /**
     * Retrive point for [Author] via tag
     *
     * Operator to find an author in the team by their tag; to be used with brackets `config[TAG]`
     *
     * @param index the tag of the desired author
     * @return an [Author], if there is a match; null otherwise
     */
    operator fun get(index: String): Author? = team.find { it.tag == index }

    /**
     * Retrive point for list of [Author] via [NamedTeams]
     *
     * Operator to find a list of [Author] in the team by the named team; to be used with brackets `config[NamedTeams]`
     *
     * @param namedTeams the map of named teams with relative tags
     * @return a list of unique [Author], if there is a match; an empty list otherwhise
     */
    operator fun get(namedTeams: NamedTeams): List<Author> =
        team.filter { namedTeams.values.flatten().contains(it.tag) }.distinct()

}
