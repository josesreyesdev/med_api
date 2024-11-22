package com.jsrdev.med_api.physician

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer

class SpecialtyDeserializer : JsonDeserializer<Specialty>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Specialty {
        val value = p.text
        return Specialty.fromString(value)
            ?: throw IllegalArgumentException("Invalid specialty: $value")
    }
}