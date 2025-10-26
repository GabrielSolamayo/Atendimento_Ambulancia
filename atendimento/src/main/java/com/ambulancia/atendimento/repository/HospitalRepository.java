package com.ambulancia.atendimento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.Hospital;


public interface HospitalRepository extends JpaRepository<Hospital, Integer> {
	
	//Método para saber se existe hospital na tabela Hospitais;
	boolean existsByNomeHospital(String nomeHospital);
}
