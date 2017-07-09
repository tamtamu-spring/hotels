package com.mnazarenka.configuration;

import com.mnazarenka.aspect.ServicesLogger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {

    @Bean
    public ServicesLogger serviceLogger(){
        return new ServicesLogger();
    }
}
