package es.malvarez.microservices.accelerator.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import java.security.SecureRandom;
import java.util.Random;

/**
 * Configuration beans
 */
@Configuration
public class AcceleratorConfig {

    @Bean
    public ThreadPoolTaskScheduler acceleratorExecutor() {
        ThreadPoolTaskScheduler executor = new ThreadPoolTaskScheduler();
        executor.setBeanName("acceleratorExecutor");
        return executor;
    }

    @Bean
    public Random randomizer() {
        return new SecureRandom();
    }
}
