package br.com.yagovcb.vendedorapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class VendedorApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(VendedorApiApplication.class, args);
    }

}
