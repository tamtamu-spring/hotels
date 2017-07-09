package com.mnazarenka.configuration;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.mnazarenka.service")
@Import({DaoConfig.class, AspectConfig.class})
public class ServiceConfig {

    @Bean
    public Logger logger(){
        return Logger.getLogger(Logger.class);
    }
}
