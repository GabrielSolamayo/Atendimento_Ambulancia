package com.ambulancia.atendimento.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.Hospital;
import com.ambulancia.atendimento.repository.BairroRepository;
import com.ambulancia.atendimento.repository.HospitalRepository;
import com.ambulancia.atendimento.service.AmbulanciaAtendimento;

@Controller
public class HomeController {
	
	@Autowired
    private BairroRepository bairroRepository;
	
	@Autowired
	private AmbulanciaAtendimento ambulanciaAtendimento;
	
	@GetMapping("/")
	public String index() {
		return "index";// nome do arquivo HTML que você quer exibir
	}
	
	//Carrega a pagina com os bairros;
	@GetMapping("/main")
	public String mainPage(Model model) {
		List<Bairro> bairros = bairroRepository.findAll();
		model.addAttribute("bairros", bairros);
		return "main"; // o nome do arquivo HTML sem .html
	}
	
	//Processa o bairro selecionado e mostra o resultado;
	@PostMapping("/buscarHospital")
	public String buscarHospital(@RequestParam("bairro")String bairroNome, Model model) {
		Hospital hospitalEncontrado = ambulanciaAtendimento.encontrarHospital(bairroNome);
		
		model.addAttribute("bairros", bairroRepository.findAll());
		model.addAttribute("hospital", hospitalEncontrado);
		model.addAttribute("bairroSelecionado", bairroNome);
		
		return "main"; //Retorna a mesma pagina, mas agora com resultado;
	}
}
