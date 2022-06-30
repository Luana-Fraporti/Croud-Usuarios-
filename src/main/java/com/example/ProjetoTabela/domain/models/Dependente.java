package com.example.ProjetoTabela.domain.models;

import com.example.ProjetoTabela.domain.models.enums.EnumTipoDeParentesco;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;


@Entity
@Table(name = "t_dependentes")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties({"user"})
public class Dependente {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "d_nome")
    private String nome;

    @Column(name = "d_sobrenome")
    private String sobrenome;

    @Column(name = "d_email")
    private String email;

    @Column(name = "d_idade")
    private Integer idade;

    @Column(name = "d_ativo")
    private Boolean ativo;

    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = LAZY)
    private Usuario user;

    @Column(name = "tipo_de_parentesco")
    private EnumTipoDeParentesco TipoDeParentesco;

    public Dependente(Long id, String nome, String sobrenome, String email, Integer idade, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.idade = idade;
        this.ativo = ativo;
    }

    public boolean isAtivo() {
        return this.ativo;
    }
}

