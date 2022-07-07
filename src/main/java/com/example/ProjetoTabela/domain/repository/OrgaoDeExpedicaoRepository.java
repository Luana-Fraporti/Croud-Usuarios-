package com.example.ProjetoTabela.domain.repository;

import com.example.ProjetoTabela.domain.models.OrgaoDeExpedicao;
import com.example.ProjetoTabela.v1.DTO.OrgaoDeExpedicaoDTO;
import com.example.ProjetoTabela.v1.DTO.iDocumentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface OrgaoDeExpedicaoRepository extends JpaRepository<OrgaoDeExpedicao,Long> {
    @Query(value = "select "
            + "t_orgao_expedicao.id,t_orgao_expedicao.nome "
            + "from t_orgao_expedicao "
            ,nativeQuery = true )
    List<OrgaoDeExpedicaoDTO> obterOrgaoDeExpedicao();
}