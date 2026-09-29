package br.com.gpfaltz.posto_combustivel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;

public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {
    boolean existsByBombaId(Long bombaId);
}