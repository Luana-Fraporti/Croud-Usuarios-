package com.example.ProjetoTabela.domain.repository;


import com.example.ProjetoTabela.domain.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario,Long> {

}
