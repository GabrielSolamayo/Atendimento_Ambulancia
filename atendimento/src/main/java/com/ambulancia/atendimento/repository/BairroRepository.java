package com.ambulancia.atendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.ambulancia.atendimento.model.Bairro;
import org.springframework.stereotype.Repository;


@Repository //Diz ao Spring que essa interface é um componente de acesso a dados;
public interface BairroRepository extends JpaRepository<Bairro, Integer> {
	
	Bairro findByNomeBairro(String nomeBairro);
}
