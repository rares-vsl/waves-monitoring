package domain

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = ConsumptionTypeSerializer::class)
enum class ConsumptionType {
    GAS,
    WATER,
    ELECTRICITY,
}

object ConsumptionTypeSerializer : KSerializer<ConsumptionType> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("ConsumptionType", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: ConsumptionType) {
        encoder.encodeString(value.name.lowercase())
    }

    override fun deserialize(decoder: Decoder): ConsumptionType {
        return ConsumptionType.valueOf(decoder.decodeString().uppercase())
    }
}