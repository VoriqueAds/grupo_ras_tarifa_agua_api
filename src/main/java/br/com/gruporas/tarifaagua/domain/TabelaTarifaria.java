package br.com.gruporas.tarifaagua.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tariff_table")
public class TabelaTarifaria {

  @Id
  private UUID id;

  @Column(name = "name", nullable = false, length = 120)
  private String nome;

  @Column(name = "valid_from")
  private LocalDate vigenciaInicio;

  @Column(name = "valid_to")
  private LocalDate vigenciaFim;

  @Column(name = "active", nullable = false)
  private boolean ativa = true;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime criadoEm = OffsetDateTime.now();

  protected TabelaTarifaria() {}

  public TabelaTarifaria(UUID id, String nome, LocalDate vigenciaInicio, LocalDate vigenciaFim, boolean ativa) {
    this.id = id;
    this.nome = nome;
    this.vigenciaInicio = vigenciaInicio;
    this.vigenciaFim = vigenciaFim;
    this.ativa = ativa;
    this.criadoEm = OffsetDateTime.now();
  }

  public UUID getId() { return id; }
  public String getNome() { return nome; }
  public LocalDate getVigenciaInicio() { return vigenciaInicio; }
  public LocalDate getVigenciaFim() { return vigenciaFim; }
  public boolean isAtiva() { return ativa; }
  public OffsetDateTime getCriadoEm() { return criadoEm; }

  public void desativar() { this.ativa = false; }
}
