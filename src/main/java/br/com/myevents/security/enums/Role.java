package br.com.myevents.security.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

/**
 * Representa um cargo de usuário.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public enum Role {

    ADMIN(0, "ROLE_ADMIN"),
    USER(1, "ROLE_USER");

    /**
     * O identificador de um cargo.
     */
    @ToString.Exclude private int id;

    /**
     * O nome de um cargo.
     */
    private String name;

    /**
     * Retorna o cargo de um usuário contendo o identificador especificado.
     *
     * @param id o identificador de um cargo
     * @return o cargo de um usuário
     * @throws IllegalArgumentException se não existir um cargo cadastrado para o identificador
     *      especificado
     */
    public static Role of(int id) {
        return Stream.of(Role.values())
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhum cargo cadastrado para o identificador %d.", id)));
    }

}
