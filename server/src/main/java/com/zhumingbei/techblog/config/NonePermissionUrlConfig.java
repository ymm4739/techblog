package com.zhumingbei.techblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "url.ignore")
@Data
public class NonePermissionUrlConfig {
    private List<String> pattern = new ArrayList<>();
    private List<String> get = new ArrayList<>();
    private List<String> post = new ArrayList<>();
}
