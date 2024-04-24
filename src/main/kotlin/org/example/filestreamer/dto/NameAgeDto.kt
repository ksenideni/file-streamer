package org.example.filestreamer.dto

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder

data class NameAgeDto(
    @JsonProperty("name") val name: String,
    @JsonProperty("age") val age: Int
)
