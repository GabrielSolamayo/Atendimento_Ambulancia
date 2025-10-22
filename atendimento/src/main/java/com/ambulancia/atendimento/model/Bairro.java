package com.ambulancia.atendimento.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import jakarta.persistence.*;


@Entity // indica que a classe representa uma tabela do banco;
@Table(name = "bairros") // indica o nome exato da tabela;
public class Bairro implements Serializable{
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBairro")
	private int idBairro;
	
	@Column(name = "nome_bairro", nullable = false, length = 45)
	private String nomeBairro;
	
	@OneToMany(mappedBy = "bairro", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Hospital> hospitais;
	
	public Bairro() {
		
	}

	public Bairro(int idBairro, String nomeBairro) {
		//super();
		this.idBairro = idBairro;
		this.nomeBairro = nomeBairro;
	}

	public int getIdBairro() {
		return idBairro;
	}

	public void setIdBairro(int idBairro) {
		this.idBairro = idBairro;
	}

	public String getNomeBairro() {
		return nomeBairro;
	}

	public void setNomeBairro(String nomeBairro) {
		this.nomeBairro = nomeBairro;
	}

	@Override
	public String toString() {
		return "Bairro [idBairro=" + idBairro + ", nomeBairro=" + nomeBairro + "]";
	}
	
	
}
