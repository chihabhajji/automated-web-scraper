package tn.globalnet.webscraper.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "my.scraper")
public class ScraperConfig {
    public String targetUrl;
    public String loginUsernameSelector;
    public String loginUsernameValue;
    public String loginPasswordSelector;
    public String loginPasswordValue;
    public String loginButtonSelector;
    public String exportButtonSelector;
}
