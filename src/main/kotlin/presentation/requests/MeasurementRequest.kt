package presentation.requests

import kotlinx.serialization.Serializable

@Serializable
data class MeasurementRequest(
    val realTimeConsumption: Float,
    val timestamp: String,
)