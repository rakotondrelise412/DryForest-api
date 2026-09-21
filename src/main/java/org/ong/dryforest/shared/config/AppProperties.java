package org.ong.dryforest.shared.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app.property")
@Getter
@Setter
public class AppProperties {
    private String frontendDomain;
    private String jwtSigningKey;
    private long jwtExpirationDate;
    private long maxAge;
    private String headerType;
}