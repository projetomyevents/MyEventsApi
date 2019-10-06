package br.com.myevents.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UsuarioDTO {
	
	private String email;
	private String cpf;
	private String nome;
	@JsonIgnore
	private String senha;
	private String celular;

}
