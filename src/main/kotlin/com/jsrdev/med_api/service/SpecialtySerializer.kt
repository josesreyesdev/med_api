package com.jsrdev.med_api.service

import com.jsrdev.med_api.physician.Specialty
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object SpecialtySerializer : KSerializer<Specialty> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Specialty", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Specialty {
        val value = decoder.decodeString()
        return Specialty.fromString(value)
            ?: throw SerializationException("Invalid specialty: $value")
    }

    override fun serialize(encoder: Encoder, value: Specialty) {
        encoder.encodeString(value.specialtyEng) // serialize to eng
    }
}