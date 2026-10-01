package br.com.gpfaltz.posto_combustivel.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.lang.reflect.Field;
import jakarta.persistence.*;

/**
 * Testes unitários para a entidade {@link Abastecimento}.
 * Verifica builder, getters/setters, equals/hashCode e anotações JPA.
 *
 * <p>Exemplo de execução:</p>
 * <pre>
 * mvn test -Dtest=AbastecimentoTest
 * </pre>
 */
class AbastecimentoTest {

    /**
     * Verifica o padrão builder e os getters/setters.
     */
    @Test
    void builderAndGettersSetters() {
        Combustivel combustivel = Combustivel.builder()
                .id(1L)
                .nome("Gasolina")
                .precoPorLitro(new BigDecimal("5.199"))
                .build();
        Bomba bomba = Bomba.builder()
                .id(2L)
                .nome("Bomba A")
                .combustivel(combustivel)
                .build();
        Abastecimento a = Abastecimento.builder()
                .id(3L)
                .bomba(bomba)
                .data(LocalDate.of(2024, 5, 10))
                .volume(new BigDecimal("40"))
                .valorTotal(new BigDecimal("207.96"))
                .build();
        assertThat(a.getId()).isEqualTo(3L);
        assertThat(a.getBomba()).isSameAs(bomba);
        assertThat(a.getData()).isEqualTo(LocalDate.of(2024, 5,10));
        assertThat(a.getVolume()).isEqualByComparingTo("40");
        assertThat(a.getValorTotal()).isEqualByComparingTo("207.96");
    }

    /**
     * Verifica equals e hashCode.
     */
    @Test
    void equalsAndHashCode() {
        Combustivel c = Combustivel.builder().id(1L).nome("X").precoPorLitro(BigDecimal.ONE).build();
        Bomba b = Bomba.builder().id(2L).nome("Y").combustivel(c).build();
        Abastecimento a1 = Abastecimento.builder().id(3L).bomba(b).data(LocalDate.now()).volume(BigDecimal.TEN).valorTotal(BigDecimal.valueOf(100)).build();
        Abastecimento a2 = Abastecimento.builder().id(3L).bomba(b).data(a1.getData()).volume(BigDecimal.TEN).valorTotal(BigDecimal.valueOf(100)).build();
        Abastecimento a3 = Abastecimento.builder().id(4L).bomba(b).data(LocalDate.now()).volume(BigDecimal.ONE).valorTotal(BigDecimal.ONE).build();
        assertThat(a1).isEqualTo(a2);
        assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
        assertThat(a1).isNotEqualTo(a3);
    }

    /**
     * Verifica a presença das anotações JPA.
     */
    @Test
    void jpaAnnotationsPresent() throws Exception {
        assertThat(Abastecimento.class.isAnnotationPresent(Entity.class)).isTrue();
        Field id = Abastecimento.class.getDeclaredField("id");
        assertThat(id.isAnnotationPresent(Id.class)).isTrue();
        assertThat(id.isAnnotationPresent(GeneratedValue.class)).isTrue();
        Field bomba = Abastecimento.class.getDeclaredField("bomba");
        assertThat(bomba.isAnnotationPresent(ManyToOne.class)).isTrue();
        assertThat(bomba.isAnnotationPresent(JoinColumn.class)).isTrue();
        JoinColumn jc = bomba.getAnnotation(JoinColumn.class);
        assertThat(jc.name()).isEqualTo("bomba_id");
        assertThat(jc.nullable()).isFalse();
        Field data = Abastecimento.class.getDeclaredField("data");
        Column colData = data.getAnnotation(Column.class);
        assertThat(colData).isNotNull();
        assertThat(colData.nullable()).isFalse();
        Field volume = Abastecimento.class.getDeclaredField("volume");
        Column colVol = volume.getAnnotation(Column.class);
        assertThat(colVol).isNotNull();
        assertThat(colVol.nullable()).isFalse();
        assertThat(colVol.precision()).isEqualTo(10);
        assertThat(colVol.scale()).isEqualTo(3);
        Field valor = Abastecimento.class.getDeclaredField("valorTotal");
        Column colValor = valor.getAnnotation(Column.class);
        assertThat(colValor).isNotNull();
        assertThat(colValor.nullable()).isFalse();
        assertThat(colValor.precision()).isEqualTo(10);
        assertThat(colValor.scale()).isEqualTo(3);
    }
}