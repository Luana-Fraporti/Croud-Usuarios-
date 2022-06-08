package com.example.ProjetoTabela.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity (name = "t_usuarios")
@Table (name = "t_usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Usuario {

    @Id
    @Column(updatable = false,nullable = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    private String nome;

    @Column(name = "sobrenome")
    private String sobrenome;

    @Column(name = "email")
    private String email;

    @Column(name = "idade")
    private int idade;

    @Column(name = "ativo")
    private boolean ativo;
    public Usuario(Usuario u) {
        this.id = u.id;
        this.nome = u.nome;
        this.sobrenome = u.sobrenome;
        this.email = u.email;
        this.idade = u.idade;
        this.ativo = u.ativo;
    }

    public Usuario(String john, String john1, String s, int i, String ativo) {
    }


}