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

    ADMIN(0, "ADMIN"),
    USER(1, "USER");

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
     * @throws IllegalArgumentException se não existir um cargo registrado para o identificador especificado
     */
    public static Role of(int id) {
        return Stream.of(Role.values())
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhum cargo com o identificador '%d' registrado.", id)));
    }

    /**
     * Retorna o cargo de um convidado contendo o nome especificado.
     *
     * @param name o nome de um cargo
     * @return o cargo de um convidado
     * @throws IllegalArgumentException se não existir um cargo registrado para o identificador
     */
    public static Role of(String name) {
        return Stream.of(Role.values())
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhum cargo com o nome '%s' registrado.", name)));
    }

}
