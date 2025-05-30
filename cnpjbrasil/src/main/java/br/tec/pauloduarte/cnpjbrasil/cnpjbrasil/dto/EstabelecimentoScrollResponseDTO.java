package br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.dto;

import java.util.List;

import br.tec.pauloduarte.cnpjbrasil.cnpjbrasil.model.Estabelecimento;
import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class EstabelecimentoScrollResponseDTO {
    private List<Estabelecimento> data;
    private Long lastId;
    private boolean hasNext;
}
