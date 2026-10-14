package kz.iitu.springlab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SpringlabApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringlabApplication.class, args);
	}
}