package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "paises")
public class Pais {
    @Id
    @Column(name = "codigo", length = 10)
    private String codigo;
    
    @Column(name = "descricao", nullable = false, length = 255)
    private String descricao;
}