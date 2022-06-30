package com.example.ProjetoTabela.v1.DTO;

import java.time.LocalDate;

public interface iDocumentoDTO {
    Long getId();

    String getTipoDeDocumento();

    String getNumero();

    LocalDate getDataDeExpedicao();

    String getOrgaoExpedicao();
}
