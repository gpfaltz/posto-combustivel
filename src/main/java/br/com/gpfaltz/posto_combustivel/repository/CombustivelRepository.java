package br.com.gpfaltz.posto_combustivel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.gpfaltz.posto_combustivel.entity.Combustivel;

public interface CombustivelRepository extends JpaRepository<Combustivel, Long> {
	// ...existing code...
}
