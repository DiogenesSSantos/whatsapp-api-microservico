package com.git.devdioge.whatsapp_api.config

import org.springframework.boot.restclient.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.client.RestTemplate
import java.time.Duration

@Configuration
class ConfigurationGlobal {


    @Bean
    fun configuracaoRestTemplate() : RestTemplate {
        return RestTemplateBuilder()
            .rootUri("http://localhost:8080/paciente-api")
            .connectTimeout(Duration.ofSeconds(10))
            .readTimeout(Duration.ofSeconds(10))
            .build()
    }
}