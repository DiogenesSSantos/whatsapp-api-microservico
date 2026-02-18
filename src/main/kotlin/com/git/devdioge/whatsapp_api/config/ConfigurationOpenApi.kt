package com.git.devdioge.whatsapp_api.config

import io.swagger.v3.oas.models.OpenAPI
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Diogenes Santos
 * Classe de configuração dos nosso beans Globais.
 */
@Configuration
class ConfigurationOpenApi {

    @Bean
    fun configOpenApi() : OpenAPI {
        return OpenAPI()
    }

    @Bean
    fun configQrController() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("QrCodeController")
            .pathsToMatch("/whatsapp/conexao/**")
            .build()
    }
}