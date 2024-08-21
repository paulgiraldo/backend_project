package com.codigo.ms_maestros.client;

import com.codigo.ms_maestros.response.ValidaTokenDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "client-validatoken", url = "http://localhost:8081/api/v1/")
public interface ClientValidaToken {
    @GetMapping("/validatoken")
    ValidaTokenDto getInfoValidaToken(@RequestParam("token") String token,
                               @RequestParam("endPoint") String endPoint);
}
