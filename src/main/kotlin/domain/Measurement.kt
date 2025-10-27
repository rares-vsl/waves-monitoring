package domain

import kotlinx.serialization.Serializable

@Serializable
class Measurement(
    val hookupID: String,
    val consumptionType: ConsumptionType,
    val consumptionValue: Float,
)

