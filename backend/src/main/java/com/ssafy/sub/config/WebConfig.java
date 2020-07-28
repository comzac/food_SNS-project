//package com.ssafy.sub.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//        		.allowedOrigins("*")
//        		.allowedHeaders("Access-Control-Request-Headers")
//        		.allowedHeaders("Access-Control-Allow-Headers")
//        		.allowedHeaders("X-AUTH-TOKEN")
//                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
//    }
//}