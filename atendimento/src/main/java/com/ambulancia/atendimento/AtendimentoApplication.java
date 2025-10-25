package com.ambulancia.atendimento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import com.ambulancia.atendimento.service.AmbulanciaAtendimento;

@SpringBootApplication
public class AtendimentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(AtendimentoApplication.class, args);
		
		// Inicia o Spring Boot e pega o contexto da aplicação
		ApplicationContext context = SpringApplication.run(AtendimentoApplication.class, args);
		
		// Obtém o bean gerenciado pelo Spring (com @Autowired funcionando)
        AmbulanciaAtendimento atendimento = context.getBean(AmbulanciaAtendimento.class);
		
		
		 // Bairros
	    String[] bairros = {
	      "Pinheiros", "Vila Madalena", "Butantã", "Lapa", "Perdizes",
	      "Barra Funda", "Alto de Pinheiros", "Vila Leopoldina", "Jaguaré",
	      "Vila Sônia", "Morumbi", "Rio Pequeno", "Pompéia", "Jardim Paulista", "Itaim Bibi"};
	    
	    //Loop para cadastrar todos os bairro;
	    for (String bairro : bairros) atendimento.adicionarBairro(bairro);
	    
	    //Conexões - contém bairro e bairro vizinho (adjacente)
	    atendimento.conectarBairros("Pinheiros", "Vila Madalena");
	    atendimento.conectarBairros("Pinheiros", "Butantã");
	    atendimento.conectarBairros("Pinheiros", "Alto de Pinheiros");
	    atendimento.conectarBairros("Vila Madalena", "Perdizes");
	    atendimento.conectarBairros("Perdizes", "Lapa");
	    atendimento.conectarBairros("Lapa", "Barra Funda");
	    atendimento.conectarBairros("Butantã", "Jaguaré");
	    atendimento.conectarBairros("Jaguaré", "Vila Leopoldina");
	    atendimento.conectarBairros("Butantã", "Vila Sônia");
	    atendimento.conectarBairros("Vila Sônia", "Morumbi");
	    atendimento.conectarBairros("Morumbi", "Rio Pequeno");
	    atendimento.conectarBairros("Perdizes", "Pompéia");
	    atendimento.conectarBairros("Pinheiros", "Jardim Paulista");
	    atendimento.conectarBairros("Jardim Paulista", "Itaim Bibi");
	}

}
