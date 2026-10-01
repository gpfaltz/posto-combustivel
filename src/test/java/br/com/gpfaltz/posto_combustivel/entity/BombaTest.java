package br.com.gpfaltz.posto_combustivel.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;
import java.lang.reflect.Field;
import jakarta.persistence.*;

/**
 * Testes unitários para a entidade {@link Bomba}.
 * Verifica o padrão builder, getters/setters, equals/hashCode e anotações JPA.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=BombaTest
 * </pre>
 */
class BombaTest {

    /**
     * Verifica o padrão builder e os getters/setters.
     */
    @Test
    void builderAndGettersSetters() {
        Combustivel combustivel = Combustivel.builder()
                .id(10L)
                .nome("Etanol")
                .precoPorLitro(new BigDecimal("4.799"))
                .build();
        Bomba b = Bomba.builder()
                .id(5L)
                .nome("Bomba 1")
                .combustivel(combustivel)
                .build();
        assertThat(b.getId()).isEqualTo(5L);
        assertThat(b.getNome()).isEqualTo("Bomba 1");
        assertThat(b.getCombustivel()).isSameAs(combustivel);
    }

    /**
     * Verifica equals e hashCode.
     */
    @Test
    void equalsAndHashCode() {
        Combustivel c1 = Combustivel.builder().id(1L).nome("A").precoPorLitro(BigDecimal.ONE).build();
        Bomba b1 = Bomba.builder().id(2L).nome("B").combustivel(c1).build();
        Bomba b2 = Bomba.builder().id(2L).nome("B").combustivel(c1).build();
        Bomba b3 = Bomba.builder().id(3L).nome("C").combustivel(c1).build();
        assertThat(b1).isEqualTo(b2);
        assertThat(b1.hashCode()).isEqualTo(b2.hashCode());
        assertThat(b1).isNotEqualTo(b3);
    }

    /**
     * Verifica a presença das anotações JPA.
     */
    @Test
    void jpaAnnotationsPresent() throws Exception {
        assertThat(Bomba.class.isAnnotationPresent(Entity.class)).isTrue();
        Field id = Bomba.class.getDeclaredField("id");
        assertThat(id.isAnnotationPresent(Id.class)).isTrue();
        assertThat(id.isAnnotationPresent(GeneratedValue.class)).isTrue();
        Field nome = Bomba.class.getDeclaredField("nome");
        Column colNome = nome.getAnnotation(Column.class);
        assertThat(colNome).isNotNull();
        assertThat(colNome.nullable()).isFalse();
        Field combustivel = Bomba.class.getDeclaredField("combustivel");
        assertThat(combustivel.isAnnotationPresent(ManyToOne.class)).isTrue();
        assertThat(combustivel.isAnnotationPresent(JoinColumn.class)).isTrue();
        JoinColumn jc = combustivel.getAnnotation(JoinColumn.class);
        assertThat(jc.name()).isEqualTo("combustivel_id");
        assertThat(jc.nullable()).isFalse();
    }
}