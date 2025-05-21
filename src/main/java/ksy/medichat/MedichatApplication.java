package ksy.medichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MedichatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedichatApplication.class, args);
    }

}
