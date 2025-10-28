package com.ambulancia.atendimento.service;

import java.util.ArrayList;
import java.util.List;
import com.ambulancia.atendimento.model.Bairro;
import com.ambulancia.atendimento.model.Hospital;
import com.ambulancia.atendimento.repository.BairroRepository;
import com.ambulancia.atendimento.repository.ConexaoBairroRepository;
import com.ambulancia.atendimento.repository.HospitalRepository;
import com.ambulancia.atendimento.model.ConexaoBairro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AmbulanciaAtendimento {

	@Autowired
	private BairroRepository bairroRepository;

	@Autowired
	private ConexaoBairroRepository conexaoBairroRepository;
	
	@Autowired
	private HospitalRepository hospitalRepository;

	
	//Cadastra o Bairro em sua respectiva tabela no BD;
	public void adicionarBairro(String nome) {

		Bairro existente = bairroRepository.findByNomeBairro(nome);// Checa se já existe no banco;

		if (existente == null) { // Se o nome do bairro nao existe, cadastre;
			Bairro bairro = new Bairro();
			bairro.setNomeBairro(nome);
			bairroRepository.save(bairro);
			System.out.println("Bairro adicionado: " + nome);
		} else {
			System.out.println("Bairro já existe no banco: " + nome);
		}
	}

	public List<Bairro> listarBairros() {
		return bairroRepository.findAll();// Consulta o BD quando precisar;
	}

	
	//Cadastra a conexao de dois Bairros em sua respectiva tabela;
	public void conectarBairros(String bairroOrigem, String bairroVizinho) {

		Bairro origem = bairroRepository.findByNomeBairro(bairroOrigem);
		Bairro vizinho = bairroRepository.findByNomeBairro(bairroVizinho);

		// Verificao de Bairros existentes na conexao entre bairro;
		if (origem == null && vizinho == null) { //Se ambas nao existem;
	        System.out.println("Erro: Os bairros '" + bairroOrigem + "' e '" + bairroVizinho + "' não existem no banco.");
	        return; //Encerra o método antes de tentar salvar qualquer coisa;
	        
	    } else if (origem == null) {//Se somente bairroOrigem nao existe;
	        System.out.println("Erro: O bairro de origem '" + bairroOrigem + "' não existe no banco.");
	        return; //Encerra o método antes de tentar salvar qualquer coisa;
	        
	    } else if (vizinho == null) {//Se somente bairroVizinho nao existe;
	        System.out.println("Erro: O bairro vizinho '" + bairroVizinho + "' não existe no banco.");
	        return; //Encerra o método antes de tentar salvar qualquer coisa;
	    }
		
		// Verifica se já existe uma conexão entre os dois bairros (em qualquer direção);
		boolean conexaoExiste = conexaoBairroRepository.existsByBairroOrigemAndBairroVizinho(origem, vizinho)
		        || conexaoBairroRepository.existsByBairroOrigemAndBairroVizinho(vizinho, origem);

		if (conexaoExiste) {
		    System.out.println("A conexão entre '" + bairroOrigem + "' e '" + bairroVizinho + "' já existe.");
		    return; //Encerra o método antes de tentar salvar qualquer coisa;
		}
		
		//Cria a conexao entre bairros e salva no BD;
		ConexaoBairro conexao = new ConexaoBairro();
		conexao.setBairroOrigem(origem);
		conexao.setBairroVizinho(vizinho);
		
		conexaoBairroRepository.save(conexao);
		System.out.println("Conexão criada com sucesso entre '" + bairroOrigem + "' e '" + bairroVizinho + "'.");
	
	}
	
	
	//Cadastra Hospital em sua respectiva tabela;
	//Cadastra hospital em sua tabela respectiva;
	public void adicionarHospital(String nomeHospital, String nomeBairro, int totalLeitos, int leitosOcupados) {
		
		Bairro bairro = bairroRepository.findByNomeBairro(nomeBairro);
		boolean hospitalExiste = hospitalRepository.existsByNomeHospital(nomeHospital);
		
		//Tratamento de erro;
		if(bairro == null) { //Se o bairro nao existe;
			System.out.println("Erro: O bairro '"+nomeBairro+"' nao esta no banco");
			return;
		} else if(hospitalExiste) {//Se o hospital JA existe;
			System.out.println("Erro: Este  Hospital '"+nomeHospital+"' já esta cadastrado em um Bairro");
			return;
		}
		
		
		//Adicionando o hospital ao BD;
		Hospital hospital = new Hospital();
		hospital.setNomeHospital(nomeHospital);
		hospital.setBairro(bairro);
		hospital.setLotacaoTotal(totalLeitos);
		hospital.setLotacaoOcupada(leitosOcupados);
		
		hospitalRepository.save(hospital);
		System.out.println("Hospital: '" + nomeHospital + "' adicionado ao bairro '" + nomeBairro + "' com sucesso!");
		
	}

	
	public String encontrarHospital(String bairroInicio) {
		
		Bairro bairro = bairroRepository.findByNomeBairro(bairroInicio);
		
		if(bairro == null) {//Se o Bairro nao existir no banco;
			System.out.println("Erro: o bairro '" + bairroInicio + "' não existe no banco.");
	        return null;
		}
		
		
		
	}
	
	//Verificar o melhor hospital do Bairro atual;
	private Hospital verificarHospitalBairro(Bairro bairro) {
		List<Hospital> hospitais = hospitalRepository.findByBairro(bairro); //Lista todos os hospitais do bairro;
		if(hospitais.isEmpty()) {//Se nao houver hospitais dentro deste Bairro;
			return null;
		}
		
		Hospital melhorHospital = hospitais.get(0);//Se presume o melhor hospital o primeiro da lista (por enquanto);
		for(Hospital h : hospitais) {
			if(h.getLotacaoOcupada() < h.getLotacaoTotal()) {
				return h; //Achou uma vaga e retorna;
			}
			//Caso contrário, guarda o menos cheio para comparação posterior;
			if(h.getLotacaoOcupada() - h.getLotacaoTotal() < melhorHospital.getLotacaoOcupada() - melhorHospital.getLotacaoTotal()) {
				melhorHospital = h;
			}
		}
		return melhorHospital;//Retorna o hospital menos cheio do Bairro;
	}
}
