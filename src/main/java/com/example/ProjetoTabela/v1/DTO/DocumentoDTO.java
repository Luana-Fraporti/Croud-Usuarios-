package com.example.ProjetoTabela.v1.DTO;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.domain.models.OrgaoDeExpedicao;
import com.example.ProjetoTabela.domain.models.enums.EnumTipoDocumento;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DocumentoDTO {

    private Long id;
    private EnumTipoDocumento tipoDeDocumento;
    private String numero;
    private LocalDate dataDeExpedicao;
    private OrgaoDeExpedicaoDTO orgaoExpedicao;

    public DocumentoDTO(Documentos d) {
        this.id = d.getId();
        this.tipoDeDocumento = d.getTipoDeDocumento();
        this.numero = d.getNumero();
        this.dataDeExpedicao = d.getDataDeExpedicao();
        this.orgaoExpedicao = new OrgaoDeExpedicaoDTO(d.getOrgaoExpedicao().getId(), d.getOrgaoExpedicao().getNome());
    }
}
