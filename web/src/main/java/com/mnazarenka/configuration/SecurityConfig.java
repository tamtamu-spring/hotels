package com.mnazarenka.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;

    @Autowired
    public SecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/resources/**")
                    .permitAll()
                .antMatchers("/home", "/login", "/registration")
                    .permitAll()
                .antMatchers("/admin/**")
                    .hasAuthority("ADMIN")
                .anyRequest()
                    .authenticated()

                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/login")
                    .defaultSuccessUrl("/home")

                .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login")

                .and()
                .csrf().disable();

        http.userDetailsService(userDetailsService);
    }

}
