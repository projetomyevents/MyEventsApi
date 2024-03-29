package br.com.myevents.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

/**
 * Representa uma cidade.
 */
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString
public class City implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * A chave primária da cidade.
     */
    @Id
    private Integer id;

    /**
     * O nome da cidade.
     */
    @Column(nullable = false)
    private String name;

    /**
     * O estado da cidade.
     */
    @ManyToOne
    @JoinColumn(name = "state_id", nullable = false, foreignKey = @ForeignKey(name = "city_state_fkey"))
    private State state;

    public City(Integer id) {
        this.id = id;
    }

}
