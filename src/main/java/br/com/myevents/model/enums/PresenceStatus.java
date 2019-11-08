package br.com.myevents.model.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.stream.Stream;

/**
 * Representa um estado de presença de um convidado em um evento.
 */
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public enum PresenceStatus {

    PENDING(0, "Pendente"),
    ACCEPTED(1, "Confirmado"),
    DENIED(1, "Recusado");

    /**
     * O identificador de um estado de presença.
     */
    @ToString.Exclude private int id;

    /**
     * O nome de um estado de presença.
     */
    private String name;

    /**
     * Retorna o status de presença de um convidado contendo o identificador especificado.
     *
     * @param id o identificador de um status de presença
     * @return o status de presença de um convidado
     * @throws IllegalArgumentException se não existir um status de presença registrado para o identificador
     *      especificado
     */
    public static PresenceStatus of(int id) {
        return Stream.of(PresenceStatus.values())
                .filter(g -> g.getId() == id)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhum status de presença com o identificador '%d' registrado.", id)));
    }

    /**
     * Retorna o status de presença de um convidado contendo o nome especificado.
     *
     * @param name o nome de um status de presença
     * @return o status de presença de um convidado
     * @throws IllegalArgumentException se não existir um status de presença registrado para o identificador
     */
    public static PresenceStatus of(String name) {
        return Stream.of(PresenceStatus.values())
                .filter(g -> g.getName().equals(name))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Nenhum status de presença com o nome '%s' registrado.", name)));
    }

}
