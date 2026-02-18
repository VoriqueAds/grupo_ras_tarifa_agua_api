package br.com.gruporas.tarifaagua.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "tariff_bracket")
public class FaixaTarifaria {

  @Id
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "tariff_table_id", nullable = false)
  private TabelaTarifaria tabela;

  @Enumerated(EnumType.STRING)
  @Column(name = "category", nullable = false, length = 20)
  private CategoriaConsumidor categoria;

  @Column(name = "start_m3", nullable = false)
  private int inicio;

  @Column(name = "end_m3")
  private Integer fim;

  @Column(name = "unit_value", nullable = false, precision = 12, scale = 2)
  private BigDecimal valorUnitario;

  @Column(name = "created_at", nullable = false)
  private OffsetDateTime criadoEm = OffsetDateTime.now();

  protected FaixaTarifaria() {}

  public FaixaTarifaria(UUID id, TabelaTarifaria tabela, CategoriaConsumidor categoria, int inicio, Integer fim, BigDecimal valorUnitario) {
    this.id = id;
    this.tabela = tabela;
    this.categoria = categoria;
    this.inicio = inicio;
    this.fim = fim;
    this.valorUnitario = valorUnitario;
    this.criadoEm = OffsetDateTime.now();
  }

  public UUID getId() { return id; }
  public TabelaTarifaria getTabela() { return tabela; }
  public CategoriaConsumidor getCategoria() { return categoria; }
  public int getInicio() { return inicio; }
  public Integer getFim() { return fim; }
  public BigDecimal getValorUnitario() { return valorUnitario; }
}
