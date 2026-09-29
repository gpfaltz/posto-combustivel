package br.com.gpfaltz.posto_combustivel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gpfaltz.posto_combustivel.entity.Combustivel;

@Repository
public interface CombustivelRepository extends JpaRepository<Combustivel, Long> {
	// ...existing code...
}
