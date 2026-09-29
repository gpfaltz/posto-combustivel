package br.com.gpfaltz.posto_combustivel.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

/**
 * Entidade JPA que representa um tipo de combustível.
 *
 * <p>Campos:</p>
 * <ul>
 *   <li><b>id</b> – identificador gerado automaticamente</li>
 *   <li><b>nome</b> – nome único do combustível (ex.: "Álcool")</li>
 *   <li><b>precoPorLitro</b> – preço unitário em reais</li>
 * </ul>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Combustivel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nome;

    @Column(nullable = false, precision = 10, scale = 3)
    private BigDecimal precoPorLitro;
}