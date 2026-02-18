package br.com.gruporas.tarifaagua.repository;

import br.com.gruporas.tarifaagua.domain.TabelaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, UUID> {

  List<TabelaTarifaria> findAllByAtivaTrueOrderByCriadoEmDesc();

  Optional<TabelaTarifaria> findByIdAndAtivaTrue(UUID id);
}
