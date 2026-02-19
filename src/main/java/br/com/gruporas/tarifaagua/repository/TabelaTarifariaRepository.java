package br.com.gruporas.tarifaagua.repository;

import br.com.gruporas.tarifaagua.domain.TabelaTarifaria;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

// Repositório para a entidade TabelaTarifaria, estendendo JpaRepository para fornecer operações CRUD e consultas personalizadas. Contém métodos para buscar tabelas tarifárias ativas ordenadas por data de criação e para buscar uma tabela ativa por ID. Utilizado para acessar os dados de tabelas tarifárias no banco de dados.

public interface TabelaTarifariaRepository extends JpaRepository<TabelaTarifaria, UUID> {

  List<TabelaTarifaria> findAllByAtivaTrueOrderByCriadoEmDesc();

  Optional<TabelaTarifaria> findByIdAndAtivaTrue(UUID id);
}
