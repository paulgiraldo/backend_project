package com.codigo.ms_login.client;

import com.codigo.ms_login.dto.TrabDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client-trab", url = "http://localhost:8082/api/v1/trabajador/")
public interface ClientTrab {
    @GetMapping("/buscaxiddto/{codigo}")
    TrabDto getInfoTrab(@PathVariable String codigo);
}
