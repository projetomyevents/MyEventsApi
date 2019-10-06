package br.com.myevents.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import br.com.myevents.domain.enums.Perfil;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Usuario implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id   
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String email;
	
	private String cpf;
	private String nome;
	
	private String senha;
	private String celular;
	
	@ElementCollection(fetch=FetchType.EAGER)
	@CollectionTable(name="PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
//	@OneToMany(mappedBy="usuario")
//	List<Evento> eventos = new ArrayList<Evento>();
	
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}
	
	public Usuario(int id, String email, String cpf, String nome, String senha, String celular, Set<Integer> perfis) {
		super();
		this.id = id;
		this.email = email;
		this.cpf = cpf;
		this.nome = nome;
		this.senha = senha;
		this.celular = celular;
		addPerfil(Perfil.ADMIN);
	}
	
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public Usuario() {
		addPerfil(Perfil.ADMIN);
	}
	
}
