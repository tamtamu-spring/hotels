package com.mnazarenka.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = "com.mnazarenka.service")
@Import(DaoConfig.class)
public class ServiceConfig {
}
