package br.com.dbc.vemser.ecososapi.ecosos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class EcoSosApplication {

	public static void main(String[] args) {
		SpringApplication.run(EcoSosApplication.class, args);

	}

}
