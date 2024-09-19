package dev.makos.accounts.config.properties;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ConfigurationProperties("accounts")
public class AccountContactInfo {

    private String message;
    private Map<String, String> contactDetails;
    private List<String> onCallSupport;
}
