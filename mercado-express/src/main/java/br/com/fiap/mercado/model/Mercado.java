package br.com.fiap.mercado.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TDS_TB_MERCADO")
public class Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_MERCADO")
    @SequenceGenerator(name = "SQ_MERCADO", sequenceName = "SQ_TDS_MERCADO", allocationSize = 1)
    @Column(name = "ID")
    private Long id;

    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "TIPO", nullable = false, length = 50)
    private String tipo;

    @Column(name = "SETOR", nullable = false, length = 50)
    private String setor;

    @Column(name = "TAMANHO", length = 30)
    private String tamanho;

    @Column(name = "PRECO", nullable = false)
    private Double preco;
}
