package com.ambulancia.atendimento.service;

import java.util.ArrayList;
import java.util.List;
import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.Hospital;
import com.ambulancia.atendimento.repository.BairroRepository;
import com.ambulancia.atendimento.model.ConexaoBairro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AmbulanciaAtendimento {
    
    @Autowired
    private BairroRepository bairroRepository;
    
    
    public void adicionarBairro(String nome) {
    	
    	Bairro existente = bairroRepository.findByNomeBairro(nome);// Checa se já existe no banco;
    	
    	if(existente == null) { //Se o nome do bairro nao existe, cadastre;
    		Bairro bairro = new Bairro();
            bairro.setNomeBairro(nome);
            bairroRepository.save(bairro);
            System.out.println("Bairro adicionado: " + nome);
    	}else {
    		System.out.println("Bairro já existe no banco: " + nome);
    	}
    }
    
    public List<Bairro> listarBairros() {
        return bairroRepository.findAll();// consulta o BD quando precisar;
    }
	
}
