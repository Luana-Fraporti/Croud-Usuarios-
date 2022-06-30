package com.example.ProjetoTabela.v1.DTO;

import com.example.ProjetoTabela.domain.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrgaoDeExpedicaoDTO {
    private Long id;
    private String nome;

    public OrgaoDeExpedicaoDTO(Usuario u) {
        this.id = u.getId();
        this.nome = u.getNome();
    }
     }