package com.hamsterbusters.squeaker.jwt;

import com.google.common.net.HttpHeaders;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "application.jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private long tokenExpirationAfterMinutes;

    public String getAuthorizationHeader() {
        return HttpHeaders.AUTHORIZATION;
    }
}
