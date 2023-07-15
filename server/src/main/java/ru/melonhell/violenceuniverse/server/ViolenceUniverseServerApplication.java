package ru.melonhell.violenceuniverse.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@SpringBootApplication(scanBasePackages = "ru.melonhell.violenceuniverse.server")
public class ViolenceUniverseServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViolenceUniverseServerApplication.class, args);
    }

    @Bean
    public ScheduledExecutorService scheduledExecutorService() {
        return Executors.newScheduledThreadPool(4);
    }
}
