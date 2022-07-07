package com.example.ProjetoTabela.domain.repository;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.v1.DTO.DocumentoDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface DocumentosRepository extends JpaRepository<Documentos, Long> {
    @Query(value = "select t_documentos.id,"
            + "case when tipo_de_documento = 0 then 'RG' "
            + "when tipo_de_documento = 1 then 'CPF' "
            + "when tipo_de_documento = 2 then 'CNH' "
            + "when tipo_de_documento = 3 then 'Certidão de casamento' "
            + "when tipo_de_documento = 4 then 'Certidão de nascimento' "
            + "end as tipoDeDocumento,"
            + "numero,"
            + "data_de_expedicao as dataDeExpedicao,"
            + "t_orgao_expedicao.nome as orgaoExpedicao "
            + "from t_documentos "
            + "join t_orgao_expedicao on t_orgao_expedicao.id = t_documentos.orgao_expedicao_id "
            + "where t_documentos.user_id = :id ", nativeQuery = true)
    Set<DocumentoDTO> obterDocumentosPorIdDoUsuario(@Param("id") Long id);
}

