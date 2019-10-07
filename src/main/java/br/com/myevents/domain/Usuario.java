package br.com.myevents.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import br.com.myevents.domain.enums.Perfil;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Representa um usuário.
 */
@Entity
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária de um usuário.
     */
    @Positive
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * O email de um usuário.
     */
    @Column(nullable = false, unique = true)
    @Email private String email;

    /**
     * O nome completo de um usuário.
     */
    @Column(nullable = false)
    @NotBlank private String nome;

    /**
     * O CPF de um usuário.
     */
    @Column(nullable = false, unique = true, length = 11)
    @NotBlank @Size(min = 11, max = 11) private String cpf;

    /**
     * O número de celular de um usuário.
     */
    @Column(nullable = false, length = 19)
    @NotBlank private String celular;

    /**
     * A senha de um usuário.
     */
    @Column(nullable = false)
    @NotBlank @Size(min = 6) private String senha;

    @ElementCollection(fetch=FetchType.EAGER)
    @CollectionTable(name="PERFIS")
    private Set<Integer> perfis = new HashSet<>();

//	@OneToMany(mappedBy="usuario")
//    List<Evento> eventos = new ArrayList<Evento>();

    public Set<Perfil> getPerfis() {
        return perfis.stream().map(Perfil::of).collect(Collectors.toSet());
    }

    protected Usuario() {
        addPerfil();
    }

    protected Usuario(int id, String email, String cpf, String nome, String senha, String celular, Set<Integer> perfis) {
        super();
        this.id = id;
        this.email = email;
        this.cpf = cpf;
        this.nome = nome;
        this.senha = senha;
        this.celular = celular;
        addPerfil();
    }

    private void addPerfil() {
        perfis.add(Perfil.ADMIN.getId());
    }

}
