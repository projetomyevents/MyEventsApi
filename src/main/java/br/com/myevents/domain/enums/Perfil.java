package br.com.myevents.domain.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

/**
 * Representa um perfil de usuário.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public enum Perfil {

    ADMIN(1, "ROLE_ADMIN");

    /**
     * O identificador de um perfil.
     */
    @ToString.Exclude private int id;

    /**
     * O nome de um perfil.
     */
    private String nome;

    /**
     * Retorna o perfil de um usuário contendo o identificador especificado.
     *
     * @param id o identificador de um perfil
     * @return o perfil de um usuário
     * @throws IllegalArgumentException se não existir um perfil cadastrado para o identificador
     *      especificado
     */
    public static Perfil of(int id) {
        return Stream.of(Perfil.values())
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhum perfil cadastrado para o identificador %d.", id)));
    }

}
