package br.com.gpfaltz.posto_combustivel.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;
import java.lang.reflect.Field;
import jakarta.persistence.*;

/**
 * Testes unitários para a entidade {@link Combustivel}.
 * Verifica o padrão builder, getters/setters, equals/hashCode e anotações JPA.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=CombustivelTest
 * </pre>
 */
class CombustivelTest {

    /**
     * Verifica o padrão builder e os getters/setters.
     */
    @Test
    void builderAndGettersSetters() {
        Combustivel c = Combustivel.builder()
                .id(1L)
                .nome("Álcool")
                .precoPorLitro(new BigDecimal("4.599"))
                .build();
        assertThat(c.getId()).isEqualTo(1L);
        assertThat(c.getNome()).isEqualTo("Álcool");
        assertThat(c.getPrecoPorLitro()).isEqualByComparingTo("4.599");
    }

    /**
     * Verifica equals e hashCode.
     */
    @Test
    void equalsAndHashCode() {
        Combustivel c1 = Combustivel.builder()
                .id(2L)
                .nome("Gasolina")
                .precoPorLitro(new BigDecimal("5.199"))
                .build();
        Combustivel c2 = Combustivel.builder()
                .id(2L)
                .nome("Gasolina")
                .precoPorLitro(new BigDecimal("5.199"))
                .build();
        Combustivel c3 = Combustivel.builder()
                .id(3L)
                .nome("Diesel")
                .precoPorLitro(new BigDecimal("4.099"))
                .build();
        assertThat(c1).isEqualTo(c2);
        assertThat(c1.hashCode()).isEqualTo(c2.hashCode());
        assertThat(c1).isNotEqualTo(c3);
    }

    /**
     * Verifica a presença das anotações JPA.
     */
    @Test
    void jpaAnnotationsPresent() throws Exception {
        // Class level
        assertThat(Combustivel.class.isAnnotationPresent(Entity.class)).isTrue();
        // Fields
        Field id = Combustivel.class.getDeclaredField("id");
        assertThat(id.isAnnotationPresent(Id.class)).isTrue();
        assertThat(id.isAnnotationPresent(GeneratedValue.class)).isTrue();
        Field nome = Combustivel.class.getDeclaredField("nome");
        Column colNome = nome.getAnnotation(Column.class);
        assertThat(colNome).isNotNull();
        assertThat(colNome.nullable()).isFalse();
        assertThat(colNome.unique()).isTrue();
        Field preco = Combustivel.class.getDeclaredField("precoPorLitro");
        Column colPreco = preco.getAnnotation(Column.class);
        assertThat(colPreco).isNotNull();
        assertThat(colPreco.nullable()).isFalse();
        assertThat(colPreco.precision()).isEqualTo(10);
        assertThat(colPreco.scale()).isEqualTo(3);
    }
}