package es.malvarez.microservices.accelerator;

import es.malvarez.microservices.accelerator.stream.AcceleratorSource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

@SpringBootApplication
@EnableBinding(AcceleratorSource.class)
public class AcceleratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcceleratorApplication.class, args);
    }
}
