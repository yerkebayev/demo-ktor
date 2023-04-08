package com.example.enums
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
@Serializable(with = StatusSerializer::class)
enum class Status {
    ACTIVE,
    INACTIVE,
    NEW,
    DELETED;

    companion object {
        fun valueOf(value: String): Status = enumValueOf(value.uppercase(Locale.getDefault()))
    }
}

@OptIn(kotlinx.serialization.ExperimentalSerializationApi::class)
@Serializer(forClass = Status::class)
object StatusSerializer : KSerializer<Status> {
    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("Status", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Status) {
        encoder.encodeString(value.name.lowercase(Locale.getDefault()).replace('_', ' '))
    }

    override fun deserialize(decoder: Decoder): Status {
        return Status.valueOf(decoder.decodeString().uppercase(Locale.getDefault()).replace(' ', '_'))
    }
}

