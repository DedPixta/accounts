package dev.makos.loans.config.properties;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@ConfigurationProperties("loans")
public record LoanContactInfo(
        String message,
        Map<String, String> contactDetails,
        List<String> onCallSupport) {

}
