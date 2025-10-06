package br.com.gtechnologia.savewithme.bootstrap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "br.com.gtechnologia")
@EntityScan(basePackages = {
        "br.com.gtechnologia.savewithme.adapters.out.persistence.entity"
})
@EnableJpaRepositories(basePackages = {
        "br.com.gtechnologia.savewithme.adapters.out.persistence.repository" // onde ficam os SpringData repos/adapters
})
public class SaveWithMeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SaveWithMeApplication.class, args);
    }

}
