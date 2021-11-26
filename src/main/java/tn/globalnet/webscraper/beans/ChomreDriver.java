package tn.globalnet.webscraper.beans;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class ChomreDriver {
    public static final String OUTPUT_PATH= System.getProperty("user.dir") + File.separator + "output";

    @Bean
    public WebDriver webDriver() throws IOException {
        // Section : Changing default chrome download location
        Map<String, Object> prefs = new HashMap<>();
        // Using File.separator instead of / for better OS integration
        prefs.put("download.default_directory", OUTPUT_PATH);
        prefs.put("profile.default_content_settings.popups", 0);
        // Printing download output location
        log.info("Output will be saved to : {}", prefs.get("download.default_directory"));
        // End Section
        // Setting chrome driver location path
        System.setProperty("webdriver.chrome.driver", new ClassPathResource("chromedriver.exe").getURI().getPath());
        // Setting chrome options
        ChromeOptions options = new ChromeOptions();
        options.setExperimentalOption("prefs", prefs);
        options.setHeadless(true);
        // Instantiating chrome driver
        WebDriver webdriver = new ChromeDriver(options);
        // idk, i found this line and i liked the way it looked, sue me !
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        // Adding custom shutdown hook to automatically close browser
        Runtime.getRuntime().addShutdownHook(new Thread(webdriver::close));
        return webdriver;
    }



}
