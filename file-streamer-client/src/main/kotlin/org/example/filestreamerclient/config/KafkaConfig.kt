package org.example.filestreamerclient.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.example.filestreamerclient.dto.MyEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.serializer.JsonDeserializer


const val BOOTSTRAP_ADDRESS = "localhost:29092"
const val GROUP_ID: String = "names-group-1"

@Configuration
class KafkaConfig {

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String> {
        val configProps: MutableMap<String, Any> = HashMap()
        configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_ADDRESS
        configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        val factory = DefaultKafkaProducerFactory<String, String>(configProps)
        return KafkaTemplate(factory)
    }


    @Bean("eventContainerFactory")
    fun eventContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, MyEvent> {
        val props: MutableMap<String, Any> = HashMap()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = BOOTSTRAP_ADDRESS
        props[ConsumerConfig.GROUP_ID_CONFIG] = GROUP_ID
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = JsonDeserializer::class.java
        props[JsonDeserializer.VALUE_DEFAULT_TYPE] = "org.example.filestreamerclient.dto.MyEvent"
        props[JsonDeserializer.TRUSTED_PACKAGES] = "*"
        props[JsonDeserializer.USE_TYPE_INFO_HEADERS] = false
        val factory: ConsumerFactory<String, MyEvent> = DefaultKafkaConsumerFactory(props)
        val listener = ConcurrentKafkaListenerContainerFactory<String, MyEvent>()
        listener.consumerFactory = factory
        return listener
    }
}