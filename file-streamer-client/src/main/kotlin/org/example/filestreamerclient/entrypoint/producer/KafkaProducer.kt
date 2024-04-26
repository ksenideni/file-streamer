package org.example.filestreamerclient.entrypoint.producer

import org.slf4j.LoggerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

val logger = LoggerFactory.getLogger(KafkaProducer::class.java)
const val TOPIC: String = "names"

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun sendEvent(event: String) {
        logger.info("start to produce event:$event")
        kafkaTemplate.send(TOPIC, event)
        logger.info("Producer produced the message {}", event)
    }

}