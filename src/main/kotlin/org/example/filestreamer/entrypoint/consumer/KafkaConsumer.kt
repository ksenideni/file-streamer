package org.example.filestreamer.entrypoint.consumer

import org.example.filestreamer.config.GROUP_ID
import org.example.filestreamer.dto.MyEvent
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

val logger = LoggerFactory.getLogger(KafkaConsumer::class.java)
const val TOPIC: String = "names"


@Component
class KafkaConsumer {
    @KafkaListener(containerFactory = "eventContainerFactory", topics = [TOPIC], groupId = GROUP_ID)
    fun flightEventConsumer(message: MyEvent) {
        logger.info("Consumer consume Kafka message -> {}", message)
    }

}