package com.example.springbootdemo.student;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Configuration
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository repository) {
        return args -> {
            Student pawel = new Student(
                    "Paweł",
                    "Ruta",
                    LocalDate.of(1984, Month.APRIL, 19),
                    "rutapoczta@gmail.com"
            );
            Student gosia = new Student(
                    "Małgorzata",
                    "Zalewska",
                    LocalDate.of(1990, Month.NOVEMBER, 15),
                    "rutapoczta@gmail.com"
            );

            repository.saveAll(List.of(pawel,gosia));
        };
    }
}
