package br.com.gpfaltz.posto_combustivel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.gpfaltz.posto_combustivel.entity.Bomba;

@Repository
public interface BombaRepository extends JpaRepository<Bomba, Long> {
	boolean existsByCombustivelId(Long combustivelId);
}