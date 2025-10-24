package com.ambulancia.atendimento.model;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "conexoes_bairros")
public class ConexaoBairro implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConexoes_bairros")
	private int idConexao;
	
	//Bairro de origem (ligação para a tabela 'bairros')
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bairro_origem_id", nullable = false)
	private Bairro bairroOrigem;
	
	// Bairro vizinho (ligação para a tabela 'bairros')
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bairro_vizinho_id", nullable = false)
	private Bairro bairroVizinho;
	
	//Construtor Padrao;
	public ConexaoBairro(int idConexao, Bairro bairroOrigem, Bairro bairroVizinho) {
		super();
		this.idConexao = idConexao;
		this.bairroOrigem = bairroOrigem;
		this.bairroVizinho = bairroVizinho;
	}
	
	//Construtor Vazio;
	public ConexaoBairro() {
		
	}

	
	//GETTERS AND SETTERS;
	
	public int getIdConexao() {
		return idConexao;
	}

	public void setIdConexao(int idConexao) {
		this.idConexao = idConexao;
	}

	public Bairro getBairroOrigem() {
		return bairroOrigem;
	}

	public void setBairroOrigem(Bairro bairroOrigem) {
		this.bairroOrigem = bairroOrigem;
	}

	public Bairro getBairroVizinho() {
		return bairroVizinho;
	}

	public void setBairroVizinho(Bairro bairroVizinho) {
		this.bairroVizinho = bairroVizinho;
	}

	@Override
	public String toString() {
		return "ConexaoBairro [idConexao=" + idConexao + ", bairroOrigem=" + bairroOrigem + ", bairroVizinho="
				+ bairroVizinho + "]";
	}

	
	
}


