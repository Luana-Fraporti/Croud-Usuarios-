package com.example.ProjetoTabela.v1.DTO;

import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.domain.models.enums.EnumTipoDeParentesco;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DependenteDTO {
    private Long id;
    private String nome;
    private String sobrenome;
    private String email;
    private Integer idade;
    private Boolean ativo;
    private EnumTipoDeParentesco TipoDeParentesco;

    public DependenteDTO(Dependente d) {
        this.id = d.getId();
        this.nome = d.getNome();
        this.sobrenome = d.getSobrenome();
        this.email = d.getEmail();
        this.idade = d.getIdade();
        this.ativo = d.isAtivo();
        TipoDeParentesco = d.getTipoDeParentesco();
    }
}
