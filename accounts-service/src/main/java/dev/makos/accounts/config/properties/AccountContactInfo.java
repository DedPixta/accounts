package dev.makos.accounts.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("accounts")
public record AccountContactInfo(
        String message,
        Map<String, String> contactDetails,
        List<String> onCallSupport) {

}
