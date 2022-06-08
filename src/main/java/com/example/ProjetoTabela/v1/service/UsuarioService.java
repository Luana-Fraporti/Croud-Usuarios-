package com.example.ProjetoTabela.v1.service;

import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.domain.repository.UsuarioRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UsuarioService {
    UsuarioRepository usuarioRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario update(Usuario usuario) {
        usuarioRepository.findById(usuario.getId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,
                "Usuário não existe, não dá pra atualizar quem não existe"));
        try {
            return usuarioRepository.save(usuario);
        }
        //A ordem do catch deve seguir também a ordem de herança e polimorfismo
        catch (DataIntegrityViolationException dive) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não é possível salvar usuário com mesmo e-mail");
        } catch (RuntimeException re) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Nao foi possivel atualizar o usuario, entre em contato com o suporte");
        }
    }

    public Usuario retrieve(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Não há registros na base"));
    }

    public  Usuario save(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (RuntimeException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível salvar o usuário");
        }
    }

    public void delete(Long id) {
        if (usuarioRepository.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não existe usuário na base com esse ID para deletar");
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível excluir o Usuário de ID " + id);
        }
    }
}

