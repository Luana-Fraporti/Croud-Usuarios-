package com.example.ProjetoTabela.v1.DTO;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.domain.models.Usuario;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class UsuarioDTO {
    private Long id;
    private String nome;
    private String email;
    private int idade;
    private String ativo;
    private Set<DependenteDTO> dependente;
    private Set<DocumentoDTO> documentos;

    public UsuarioDTO(Usuario u) {
        this.id = u.getId();
        this.nome = u.getNome().trim() + " " + u.getSobrenome();
        this.email = u.getEmail();
        this.idade = u.getIdade();
        this.ativo = u.isAtivo() ? "Está ativo" : "Não está ativo ";

        if (Objects.nonNull(u.getDocumentos())) {
            u.getDocumentos().forEach(documento -> {
                this.documentos.add(new DocumentoDTO(documento));
            });
        }
        if (Objects.nonNull(u.getDependente())) {
            u.getDependente().forEach(dependente -> {
                this.dependente.add(new DependenteDTO(dependente));
            });
        }
    }

    public UsuarioDTO(iUsuarioDTO u) {
        this.id = u.getId();
        this.nome = u.getNomeCompleto();
        this.email = u.getEmail();
        this.idade = u.getIdade();
        this.ativo = u.getAtivo();
    }
}
