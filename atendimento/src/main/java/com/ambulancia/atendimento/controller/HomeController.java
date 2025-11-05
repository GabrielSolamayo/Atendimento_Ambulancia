package com.ambulancia.atendimento.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
	private HospitalRepository hospitalRepository;
	
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
		List<Hospital> hospitais = hospitalRepository.findAll();
		model.addAttribute("bairros", bairros);
		model.addAttribute("resultadoEncontrado", false);
		model.addAttribute("hospitais", hospitais);
		model.addAttribute("modalAberto", false);
		return "main"; // o nome do arquivo HTML sem .html
	}
	
	@GetMapping("/unidadesOeste")
	public String listarUnidadesOeste(Model model) {
	    List<Hospital> hospitais = hospitalRepository.findAll(); 
	    model.addAttribute("hospitais", hospitais);
	    return "main";
	}

	
	//Processa o bairro selecionado e mostra o resultado;
	@PostMapping("/buscarHospital")
	public String buscarHospital(@RequestParam("bairro")String bairroNome, Model model) {
		Hospital hospitalEncontrado = ambulanciaAtendimento.encontrarHospital(bairroNome);
		
		List<Hospital> hospitais = hospitalRepository.findAll();
		
		model.addAttribute("bairros", bairroRepository.findAll());
		model.addAttribute("hospital", hospitalEncontrado);
		model.addAttribute("bairroSelecionado", bairroNome);
		model.addAttribute("resultadoEncontrado", hospitalEncontrado != null);
		model.addAttribute("hospitais", hospitais);
		model.addAttribute("modalAberto", false);
		
		return "main"; //Retorna a mesma pagina, mas agora com resultado;
	}
	
	@PostMapping("/abrirModal")
	public String abrirModal(Model model) {
	    model.addAttribute("modalAberto", true);
	    // recarrega também os hospitais e bairros
	    model.addAttribute("bairros", bairroRepository.findAll());
	    model.addAttribute("hospitais", hospitalRepository.findAll());
	    model.addAttribute("resultadoEncontrado", false);
	    return "main";
	}
	
	@PostMapping("/fecharModal")
	public String fecharModal(Model model) {
	    model.addAttribute("modalAberto", false);
	    model.addAttribute("bairros", bairroRepository.findAll());
	    model.addAttribute("hospitais", hospitalRepository.findAll());
	    model.addAttribute("resultadoEncontrado", false);
	    return "main";
	}
	
	
	@PostMapping("/confirmarAtendimento")
	public String confirmarAtendimento(@RequestParam("idHospital") int idHospital,  RedirectAttributes redirectAttributes) {
		Optional<Hospital> optionalHospital = hospitalRepository.findById(idHospital);
		
		if(optionalHospital.isPresent()) {
			Hospital hospital = optionalHospital.get();
			hospital.setLotacaoOcupada(hospital.getLotacaoOcupada() + 1);
			hospitalRepository.save(hospital);
			
			redirectAttributes.addFlashAttribute("mensagem", "Atendimento confirmado!");
		}else {
			redirectAttributes.addFlashAttribute("mensagem", "Hospital nao encontrado.");
		}
		
		return "redirect:/main";
	}
	
	@PostMapping("/localizarNovamente")
	public String localizarNovamente(@RequestParam("bairro") String bairroNome, Model model) {
		Hospital hospitalEncontrado = ambulanciaAtendimento.encontrarHospital(bairroNome);
		
		List<Hospital> hospitais = hospitalRepository.findAll();
		
		model.addAttribute("bairros", bairroRepository.findAll());
	    model.addAttribute("hospital", hospitalEncontrado);
	    model.addAttribute("bairroSelecionado", bairroNome);
	    model.addAttribute("resultadoEncontrado", hospitalEncontrado != null);
	    model.addAttribute("hospitais", hospitais);
	    
		return "main";
	}
}
