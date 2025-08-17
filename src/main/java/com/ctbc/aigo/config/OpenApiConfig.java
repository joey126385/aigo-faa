package com.ctbc.aigo.config;

import com.ctbc.aigo.utils.CtbcStringUtils;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Z00040866 黃勁超
 * @date 2023/04/10
 * @description Open API config
 **/
@Configuration
public class OpenApiConfig {

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Value("${swagger.application-name}")
    private String apiTitle;

    @Value("${swagger.application-version}")
    private String apiVersion;

    @Value("${api.server.base.url}")
    private String apiServerBaseUrl;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .addServersItem(new Server().url(apiServerBaseUrl + contextPath))
                .addSecurityItem(new SecurityRequirement().addList("API_ID"))
                .addSecurityItem(new SecurityRequirement().addList("API_KEY"))
                .components(
                        // Mapping to aigo id
                        new Components()
                                .addSecuritySchemes("API_ID",
                                        new SecurityScheme()
                                                .name("app_id")
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(SecurityScheme.In.HEADER)
                                        // Mapping to aigo key
                                ).addSecuritySchemes("API_KEY",
                                        new SecurityScheme()
                                                .name("app_key")
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(SecurityScheme.In.HEADER)

                                        //TODO need to solve CORS problem add CORS HEADERS
                                )
                )
                .info(new Info()
                        .title(CtbcStringUtils.convertISOToUTF8(apiTitle))
                        .version(apiVersion));
    }

}