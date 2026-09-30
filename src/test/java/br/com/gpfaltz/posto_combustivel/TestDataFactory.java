package br.com.gpfaltz.posto_combustivel;

import br.com.gpfaltz.posto_combustivel.dto.request.AbastecimentoRequest;
import br.com.gpfaltz.posto_combustivel.dto.request.BombaRequest;
import br.com.gpfaltz.posto_combustivel.dto.request.CombustivelRequest;
import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;
import br.com.gpfaltz.posto_combustivel.entity.Bomba;
import br.com.gpfaltz.posto_combustivel.entity.Combustivel;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDataFactory {
    public static Combustivel combustivel(Long id, String nome, BigDecimal preco) {
        return Combustivel.builder()
                .id(id)
                .nome(nome)
                .precoPorLitro(preco)
                .build();
    }

    public static Bomba bomba(Long id, String nome, Combustivel combustivel) {
        return Bomba.builder()
                .id(id)
                .nome(nome)
                .combustivel(combustivel)
                .build();
    }

    public static Abastecimento abastecimento(Long id, Bomba bomba, LocalDate data, BigDecimal volume, BigDecimal total) {
        return Abastecimento.builder()
                .id(id)
                .bomba(bomba)
                .data(data)
                .volume(volume)
                .valorTotal(total)
                .build();
    }

    public static AbastecimentoRequest abastecimentoRequest(Long bombaId, LocalDate data, BigDecimal volume) {
        return new AbastecimentoRequest(bombaId, data, volume);
    }

    public static BombaRequest bombaRequest(String nome, Long combustivelId) {
        return new BombaRequest(nome, combustivelId);
    }

    public static CombustivelRequest combustivelRequest(String nome, BigDecimal preco) {
        return new CombustivelRequest(nome, preco);
    }
}
