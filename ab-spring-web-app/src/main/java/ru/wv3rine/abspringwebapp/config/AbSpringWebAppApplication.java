package ru.wv3rine.abspringwebapp.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;

@SpringBootApplication
@ComponentScan("ru.wv3rine.abspringwebapp")
@EnableJdbcRepositories(basePackages = {"ru.wv3rine.abspringwebapp.dao"})
public class AbSpringWebAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AbSpringWebAppApplication.class, args);
    }

}
