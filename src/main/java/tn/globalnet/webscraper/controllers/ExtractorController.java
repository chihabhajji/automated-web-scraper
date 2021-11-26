package tn.globalnet.webscraper.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tn.globalnet.webscraper.models.Subscription;
import tn.globalnet.webscraper.services.ExtractorService;


@Slf4j
@RestController
@RequestMapping("/api")
public class ExtractorController {
    private final ExtractorService extractorService;
    @GetMapping("/scrape")
    public Mono<String> extractAndProcess(final @RequestParam(required = false, defaultValue = "") String username, final @RequestParam(required = false, defaultValue = "") String password) throws InterruptedException {
        return this.extractorService.handle(username,password);
    }
    @GetMapping("/results")
    public Iterable<Subscription> getAllSubscriptions(){
        return this.extractorService.getAll();
    }
    @Autowired
    public ExtractorController(ExtractorService extractorService){
        this.extractorService = extractorService;
    }
}
