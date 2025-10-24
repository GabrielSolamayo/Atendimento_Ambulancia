package com.ambulancia.atendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.ConexaoBairro;

public interface ConexaoBairroRepository extends JpaRepository<ConexaoBairro, Integer> {

	// Método para checar se já existe uma conexão entre dois bairros
    boolean existsByBairroOrigemAndBairroVizinho(Bairro bairroOrigem, Bairro bairroVizinho);
}
