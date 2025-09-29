package br.edu.ifpe.pontoif.pontoif;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableRabbit
public class PontoifApplication {

	public static void main(String[] args) {
		SpringApplication.run(PontoifApplication.class, args);
	}

}
