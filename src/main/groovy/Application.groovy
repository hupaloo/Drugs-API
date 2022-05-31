package main.groovy

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.PropertySource

@SpringBootApplication
@PropertySource(value = "application.properties")
class Application {

    static void main(String[] args) {
        SpringApplication.run(Application, args)
    }
}
