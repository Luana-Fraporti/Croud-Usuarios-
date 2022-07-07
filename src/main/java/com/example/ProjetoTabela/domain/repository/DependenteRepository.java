package com.example.ProjetoTabela.domain.repository;

import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.v1.DTO.iDependenteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DependenteRepository extends JpaRepository<Dependente, Long> {

    @Query(value = "select id,"
            + "concat(d_nome, ' ', d_sobrenome) as nomeCompleto,"
            + "d_email as email,"
            + "d_idade as idade,"
            + "case when d_ativo then 'Está ativo' "
            + "else 'Não está ativo' end as ativo, "
            + "case when tipo_de_parentesco = 0 then 'Mãe' "
            + "when tipo_de_parentesco = 1 then 'Conjuge' "
            + "when tipo_de_parentesco = 2 then 'Filho(a)' "
            + "when tipo_de_parentesco = 3 then 'Pai' "
            + "when tipo_de_parentesco = 4 then 'Outros' end as tipoDeParentesco "
            + "from t_dependentes td where user_id = :id "
            , nativeQuery = true)

    List<iDependenteDTO> obterDependentePorIdDoUsuario(@Param("id") Long id);

}

