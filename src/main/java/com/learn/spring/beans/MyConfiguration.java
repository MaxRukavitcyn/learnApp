package com.learn.spring.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MyConfiguration {

    @Bean
    public WebMvcConfigurerAdapter forwardToIndex() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
//                 forward requests to /admin and /user to their index.html
                registry.addViewController("/admin").setViewName(
                        "forward:/webapp/index.html");
                registry.addViewController("/user").setViewName(
                        "forward:/user/index.html");
            }
        };
    }

}
