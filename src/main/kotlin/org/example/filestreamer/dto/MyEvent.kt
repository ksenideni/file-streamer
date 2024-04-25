package org.example.filestreamer.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class MyEvent(
    @JsonProperty("name") var name: String? = null,
    @JsonProperty("age") var age: Int? = null
)
