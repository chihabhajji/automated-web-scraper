package tn.globalnet.webscraper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import tn.globalnet.webscraper.config.ScraperConfig;


@SpringBootApplication
public class WebScraperApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebScraperApplication.class, args);
    }

}
