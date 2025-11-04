package com.ambulancia.atendimento.model;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity // indica que a classe representa uma tabela do banco;
@Table(name = "hospitais") // indica o nome exato da tabela;
public class Hospital implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idHospital")
	private int idHospital;
	
	@Column(name = "nome_Hospital")
	private String nomeHospital;
	
	@Column(name = "lotacao_total")
	private int lotacaoTotal;
	
	@Column(name = "lotacao_ocupada")
	private int lotacaoOcupada;
	
	//Relacionamento com Bairro
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "idBairro", nullable = false)
	private Bairro bairro; //Relacionamento com Bairro
	
	//Construtor padrão;
	public Hospital(int dHospital, String nomeHospital, int lotacaoTotal, int lotacaoOcupada, Bairro bairro) {
		//super();
		this.idHospital = dHospital;
		this.nomeHospital = nomeHospital;
		this.lotacaoTotal = lotacaoTotal;
		this.lotacaoOcupada = lotacaoOcupada;
		this.bairro = bairro;
	}
	
	//Construtor vazio;
	public Hospital() {
		
	}
	
	//GETTER AND SETTER;

	public int getdHospital() {
		return idHospital;
	}

	public void setdHospital(int dHospital) {
		this.idHospital = dHospital;
	}

	public String getNomeHospital() {
		return nomeHospital;
	}

	public void setNomeHospital(String nomeHospital) {
		this.nomeHospital = nomeHospital;
	}

	public int getLotacaoTotal() {
		return lotacaoTotal;
	}

	public void setLotacaoTotal(int lotacaoTotal) {
		this.lotacaoTotal = lotacaoTotal;
	}

	public int getLotacaoOcupada() {
		return lotacaoOcupada;
	}

	public void setLotacaoOcupada(int lotacaoOcupada) {
		this.lotacaoOcupada = lotacaoOcupada;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	@Override
	public String toString() {
		return "Hospital [dHospital=" + idHospital + ", nomeHospital=" + nomeHospital + ", lotacaoTotal=" + lotacaoTotal
				+ ", lotacaoOcupada=" + lotacaoOcupada + ", bairro=" + bairro + "]";
	}
	
	
	
}
