package com.codigo.ms_maestros;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients()
public class MsMaestrosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsMaestrosApplication.class, args);
	}

}
