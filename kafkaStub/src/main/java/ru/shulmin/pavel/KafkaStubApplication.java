package ru.shulmin.pavel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class KafkaStubApplication {

    public static void main(String[] args) {
        SpringApplication.run(KafkaStubApplication.class, args);
    }
}
