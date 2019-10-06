package br.com.myevents.domain;


import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Evento implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter private long cdEvento;
	@Getter @Setter private String nomeCompleto;
	@Getter @Setter private LocalDate data;
	@Getter @Setter  private String precoEntrada;
	@Getter @Setter private byte limiteAcompanhantes;
	@Getter @Setter private byte idadeMinima;
	@Getter @Setter private String traje;
	@Getter @Setter private String descrição;
	@Getter @Setter private byte[] imagem;
	@Getter @Setter private String cronograma;
	@Getter @Setter private byte[] anexos;
	
}
