package com.example.ProjetoTabela.domain.models;


import com.example.ProjetoTabela.domain.models.enums.EnumTipoDocumento;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "t_documentos")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
public class Documentos {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //tipo de documento precisa ser enum
    @Column(name = "tipo_de_documento")
    private EnumTipoDocumento tipoDeDocumento;

    @Column(name = "numero")
    private String numero;

    @Column(name = "data_de_expedicao")
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataDeExpedicao;

    @JoinColumn(name = "orgao_expedicao_id")
    @ManyToOne(fetch = LAZY)
    private OrgaoDeExpedicao orgaoExpedicao;
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = LAZY)
    private Usuario user;

    public Documentos(String john, String john1, String s, int i, String aTrue) {
    }

    public Documentos(long l, String john, String john1, String s, int i, boolean b, Object o, Object o1) {
    }

    public Documentos(long id, EnumTipoDocumento rg, String numero, LocalDate dataDeExpedicao, OrgaoDeExpedicao orgaoDeExpedicao, String luana) {
    }

    public boolean isAtivo() {
        return false;
    }
}


