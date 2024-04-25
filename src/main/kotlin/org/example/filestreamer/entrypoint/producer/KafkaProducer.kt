package org.example.filestreamer.entrypoint.producer

import org.example.filestreamer.entrypoint.controller.logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun sendEvent(event: String) {
        logger.info("start to produce event:$event")
        kafkaTemplate.send(TOPIC, event)
        LOG.info("Producer produced the message {}", event)
    }

    companion object {
        const val TOPIC: String = "names"
        private val LOG = getLogger(KafkaProducer::class.java)
    }
}