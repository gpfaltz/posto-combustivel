package br.com.gpfaltz.posto_combustivel.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Entidade JPA que representa uma bomba de combustível.
 * Cada bomba está associada a um {@link Combustivel}.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Bomba {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combustivel_id", nullable = false)
    private Combustivel combustivel;
}