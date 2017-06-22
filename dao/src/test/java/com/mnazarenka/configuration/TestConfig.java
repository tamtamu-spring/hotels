package com.mnazarenka.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(Config.class)
public class TestConfig {
}
