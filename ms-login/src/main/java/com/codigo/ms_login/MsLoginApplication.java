package com.codigo.ms_login;

import com.codigo.ms_login.entities.RolesEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.http.converter.json.GsonBuilderUtils;

@SpringBootApplication
@EnableFeignClients()
public class MsLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsLoginApplication.class, args);
	}

}
