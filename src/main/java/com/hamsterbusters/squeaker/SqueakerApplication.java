package com.hamsterbusters.squeaker;

import com.hamsterbusters.squeaker.jwt.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
@EnableConfigurationProperties(value = {
        JwtConfig.class
})
public class SqueakerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqueakerApplication.class, args);
    }

    @GetMapping("/")
    public String hello() {
        return "Hello World!";
    }

}
