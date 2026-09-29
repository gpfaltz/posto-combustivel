package br.com.gpfaltz.posto_combustivel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.math.BigDecimal;

/**
 * Entidade JPA que representa um registro de abastecimento.
 * Relaciona uma {@link Bomba} a um volume de combustível em uma data.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Abastecimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bomba_id", nullable = false)
    private Bomba bomba;

    @Column(nullable = false)
    private LocalDate data;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal volume;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal valorTotal;
}