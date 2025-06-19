package ksy.medichat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

// 스프링시큐리티 기본페이지 해제
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableScheduling
public class MedichatApplication {

    public static void main(String[] args) {
        SpringApplication.run(MedichatApplication.class, args);
    }

}
