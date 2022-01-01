package com.hamsterbusters.squeaker;

import com.hamsterbusters.squeaker.jwt.JwtConfig;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigurationProperties(value = {
        JwtConfig.class
})
public class SqueakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqueakerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
