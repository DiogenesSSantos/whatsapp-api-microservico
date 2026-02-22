package com.git.devdioge.whatsapp_api.config

import io.swagger.v3.oas.models.ExternalDocumentation
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Contact
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.info.License
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
    fun config(): OpenAPI? {
        return OpenAPI().info(
            Info().title("Api whatsapp continus-deployment CI 1.0.0")
                .description(
                    "Api-Rest whatsapp"
                )
                .version("1.0.0")
                .contact(
                    Contact().url("https://diogenesssantos.github.io/meu-portfolio/")
                        .name("Diogenes S Santos").email("diogenescontatoofficial@hotmail.com")
                )
                .summary("envia mensagem para usuario utilizando outros serviço como N8N e Evolution-api")
                .license(
                    License()
                        .name("MIT License")
                        .url("https://opensource.org/licenses/MIT")
                )
        )
            .externalDocs(
                ExternalDocumentation()
                    .description("GitHub")
                    .url("https://github.com/DiogenesSSantos")
            )
            .externalDocs(
                ExternalDocumentation()
                    .description("Documentação JavaDoc")
                    .url("http://devdiogenes.shop/apidocs/index.html")
            )
    }

    @Bean
    fun configWhatsappController() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("WhatsappController")
            .pathsToMatch("/whatsapp/**")
            .build()
    }


    @Bean
    fun configQrController() : GroupedOpenApi {
        return GroupedOpenApi.builder()
            .group("QrCodeController")
            .pathsToMatch("/whatsapp/conexao/**")
            .build()
    }
}