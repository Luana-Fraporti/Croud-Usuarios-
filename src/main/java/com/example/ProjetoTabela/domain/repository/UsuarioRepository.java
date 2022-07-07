package com.example.ProjetoTabela.domain.repository;


import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.v1.DTO.iUsuarioDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query(value = "select * from t_usuarios where id = :id ", nativeQuery = true)
    Optional<Usuario> findUserById(@Param("id") Long id);

    @Query(value = "select "
            + "id,"
            + "CONCAT(nome,' ',sobrenome) as nomeCompleto,"
            + "email,"
            + "idade,"
            + "case when ativo then 'Está ativo' "
            + "else 'Não está ativo' "
            + "end as ativo "
            + "from t_usuarios "
            + "where id = :id", nativeQuery = true)
    Optional<iUsuarioDTO> findUserByIdInterface(@Param("id") Long id);

}
