package br.com.gpfaltz.posto_combustivel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gpfaltz.posto_combustivel.entity.Abastecimento;

@Repository
public interface AbastecimentoRepository extends JpaRepository<Abastecimento, Long> {
	// ...existing code...
}
