package br.com.rhinosistemas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.rhinosistemas.service.MegaSenaService;

@SpringBootApplication
public class MegaSenaApplication {

	public static void main(String[] args) {
		SpringApplication.run(MegaSenaApplication.class, args);
		
		// Parametro boolean para exibir dados do número que mais aparece para o menor
		MegaSenaService.executarLeitura(true, 60);

	}
}
