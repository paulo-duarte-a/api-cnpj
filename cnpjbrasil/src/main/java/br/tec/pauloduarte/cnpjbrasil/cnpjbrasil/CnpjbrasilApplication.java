package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CnpjbrasilApplication {

	public static void main(String[] args) {
		SpringApplication.run(CnpjbrasilApplication.class, args);
	}

}
