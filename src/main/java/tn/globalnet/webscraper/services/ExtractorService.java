package tn.globalnet.webscraper.services;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import tn.globalnet.webscraper.config.ScraperConfig;
import tn.globalnet.webscraper.models.Subscription;
import tn.globalnet.webscraper.repositories.SubscriptionRepository;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

import static tn.globalnet.webscraper.beans.ChomreDriver.OUTPUT_PATH;

@Slf4j
@Service
public class ExtractorService {


    public static final String DATA_FILE_PATH = OUTPUT_PATH + File.separator + "WFTT.txt";

    private final WebDriver driver ;
    private final SubscriptionRepository subscriptionRepository;
    @Qualifier("scraperConfig")
    private final ScraperConfig config;
    private boolean isWorking = false;
    public Mono<String> handle(String username, String password) throws InterruptedException {
        if(!isWorking){
            log.debug("-----------------------------------------------------------------------");
            log.info(config.toString());
            log.debug("-----------------------------------------------------------------------");
            isWorking = true;
            driver.get(config.targetUrl);
            driver.navigate().refresh();
            driver.findElement(By.id(config.loginUsernameSelector)).sendKeys(username.equals("") ? config.loginUsernameValue : username);
            driver.findElement(By.id(config.loginPasswordSelector)).sendKeys(password.equals("") ? config.loginPasswordValue : password);
            driver.findElement(By.id(config.loginButtonSelector)).submit();
            Thread.sleep(500);
            // Select sl = new Select(driver.findElement(By.id("")));
            // driver.findElement(By.id("ctl00_ContentPlaceHolder_txtNumTel")).sendKeys(); selectByVisibleText
            // Submit to download
            // TODO : Change to debug
            log.info("Deleted older version ? : {} " , new File(DATA_FILE_PATH).delete());

            driver.findElement(By.id(config.exportButtonSelector)).click();
            Thread.sleep(500);
            // Reading and converting section
            try (BufferedReader br=new BufferedReader(new FileReader(DATA_FILE_PATH)))
            {
                String line;
                while((line=br.readLine())!=null)
                {
                    this.subscriptionRepository.save(new Subscription(line.split("\\|")));
                }
                return Mono.empty();
            }
            catch(IOException | ParseException e)
            {
                return Mono.error(e);
            }
        } else {
            return Mono.error(new Exception("Already fetching data"));
        }
    }

    @Autowired
    public ExtractorService(WebDriver driver, SubscriptionRepository subscriptionRepository, ScraperConfig config){
        this.driver = driver;
        this.config = config;
        this.subscriptionRepository = subscriptionRepository;
    }

    public Iterable<Subscription> getAll() {
        return this.subscriptionRepository.findAll();
    }
}
