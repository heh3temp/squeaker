package com.hamsterbusters.squeaker;

import com.hamsterbusters.squeaker.jwt.JwtConfig;
import com.hamsterbusters.squeaker.user.squeaker.SqueakerProperties;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@EnableConfigurationProperties(value = {
        JwtConfig.class,
        SqueakerProperties.class
})
@SpringBootApplication
public class SqueakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqueakerApplication.class, args);
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
