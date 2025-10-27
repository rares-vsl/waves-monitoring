package domain

import kotlinx.serialization.Serializable

@Serializable()
enum class UtilityType(val value: String) {
    GAS("gas"),
    WATER("water"),
    ELECTRICITY("electricity")
}