package com.jsrdev.med_api.domain.physician

import java.text.Normalizer

enum class Specialty(val specialtyEng: String, val specialtyEsp: String) {
    ORTHOPEDICS("Orthopedics", "Ortopedia"),
    CARDIOLOGY("Cardiology", "Cardiologia"),
    GYNAECOLOGY("Gynaecology", "Ginecologia"),
    PEDIATRICS("Pediatrics", "Pediatria");

    companion object {
        fun fromString(value: String): Specialty? {
            val normalized = value.normalize()
            return entries.find {
                it.specialtyEng.equals(normalized, ignoreCase = true) ||
                        it.specialtyEsp.normalize().equals(normalized, ignoreCase = true)
            }
        }

        // function to normalize text by removing accents
        private fun String.normalize(): String =
            Normalizer.normalize(this, Normalizer.Form.NFD)
                .replace(Regex("\\p{M}"), "")
                .lowercase()
    }
}
