package dev.makos.cards.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("cards")
public record CardContactInfo(
        String message,
        Map<String, String> contactDetails,
        List<String> onCallSupport) {

}
