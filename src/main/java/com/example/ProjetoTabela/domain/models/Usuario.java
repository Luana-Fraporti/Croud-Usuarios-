package com.example.ProjetoTabela.domain.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

import static javax.persistence.FetchType.LAZY;

@Entity(name = "t_usuarios")
@Table(name = "t_usuarios")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Usuario {

    @Id
    @Column(updatable = false, nullable = false)
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

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = LAZY)
    private Set<Dependente> dependente;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = LAZY)
    private Set<Documentos> documentos;

    public Usuario(String john, String john1, String s, int i, String ativo) {
    }


}