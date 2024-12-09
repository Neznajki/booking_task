package com.booking;

import com.booking.db.repository.customRepo.RefreshableRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = RefreshableRepositoryImpl.class)
@ComponentScan({"com.booking.db.repository", "com.booking.*"})
public class HomeworkApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeworkApplication.class, args);
    }

}
