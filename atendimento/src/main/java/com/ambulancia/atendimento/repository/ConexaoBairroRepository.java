package com.ambulancia.atendimento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.ConexaoBairro;

public interface ConexaoBairroRepository extends JpaRepository<ConexaoBairro, Integer> {

	// Método para checar se já existe uma conexão entre dois bairros
    boolean existsByBairroOrigemAndBairroVizinho(Bairro bairroOrigem, Bairro bairroVizinho);
    
    
    @Query("SELECT c.bairroVizinho FROM ConexaoBairro c WHERE c.bairroOrigem = :bairro")
    List<Bairro> findVizinhos(@Param("bairro") Bairro bairro);
}
