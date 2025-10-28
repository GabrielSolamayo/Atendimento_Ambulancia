package com.ambulancia.atendimento.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.Hospital;


public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
	
	//Método para saber se existe hospital na tabela Hospitais;
	boolean existsByNomeHospital(String nomeHospital);
	
	//Método para saber o(s) hospital(ais) pertencentes a um Bairro;
	List<Hospital> findByBairro(Bairro bairro);

}
