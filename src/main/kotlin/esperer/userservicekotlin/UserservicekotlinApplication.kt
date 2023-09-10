package esperer.userservicekotlin

import feign.Logger
import org.springframework.boot.actuate.logging.LoggersEndpoint.LoggerLevels
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableDiscoveryClient
@ConfigurationPropertiesScan
@EnableFeignClients
class UserservicekotlinApplication

fun main(args: Array<String>) {
    runApplication<UserservicekotlinApplication>(*args)
}

@Bean
fun feignLoggerLevel(): Logger.Level = Logger.Level.FULL
