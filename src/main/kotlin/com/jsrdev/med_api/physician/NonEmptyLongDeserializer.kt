package com.jsrdev.med_api.physician

import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonMappingException

class NonEmptyLongDeserializer : JsonDeserializer<Long>() {
    override fun deserialize(p: JsonParser, ctxt: DeserializationContext): Long {
        val value = p.text.trim()
        return if (value.isEmpty()) {
            throw JsonMappingException.from(ctxt, "id must not be empty.")
        } else {
            value.toLongOrNull() ?: throw JsonMappingException.from(ctxt, "id must be a valid number.")
        }
    }
}