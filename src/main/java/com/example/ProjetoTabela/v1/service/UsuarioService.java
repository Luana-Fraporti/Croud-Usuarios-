package com.example.ProjetoTabela.v1.service;

import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.domain.repository.DependenteRepository;
import com.example.ProjetoTabela.domain.repository.DocumentosRepository;
import com.example.ProjetoTabela.domain.repository.UsuarioRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import com.example.ProjetoTabela.v1.DTO.UsuarioDTO;
import com.example.ProjetoTabela.v1.DTO.iUsuarioDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
@AllArgsConstructor
public class UsuarioService {
    UsuarioRepository usuarioRepository;

    DocumentosRepository documentosRepository;

    DependenteRepository dependenteRepository;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    public Usuario update(Usuario usuario) {
        usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "Usuario não encontrado para atualização"));
        try {
            if (Objects.nonNull(usuario.getDependente())) {
                usuario.getDependente().forEach(dependente -> dependente.setUser(usuario));
            }
            if (Objects.nonNull(usuario.getDocumentos())) {
                usuario.getDocumentos().forEach(documento -> documento.setUser(usuario));
            }
            return usuarioRepository.save(usuario);
        } catch (RuntimeException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível salvar o usuário");
        }
    }

    public UsuarioDTO retrieve(Long id) {
        return new UsuarioDTO(usuarioRepository.findUserById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Não há registros na base")));
    }

    public iUsuarioDTO retrieveOnlyUserInformation(Long id) {
        return usuarioRepository.findUserByIdInterface(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Não há registros na base"));
    }

    public UsuarioDTO retrieveLight(Long id) {
        UsuarioDTO user = new UsuarioDTO(usuarioRepository.findUserByIdInterface(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Não há registros na base")));
        user.setDependente();
        user.setDocumentos();
        return null;
    }

    public Usuario save(Usuario usuario) {
        try {
            Set<Dependente> dependente = new HashSet<>();
            Set<Documentos> documentos = new HashSet<>();
            if (!Objects.isNull(usuario.getDependente())) {
                for (Dependente dependente1 : usuario.getDependente()) {
                    dependente.add(dependente1);
                }
                usuario.getDependente().clear();
            }
            if (!Objects.isNull(usuario.getDocumentos())) {
                for (Documentos documento : usuario.getDocumentos()) {
                    documentos.add(documento);
                }
                usuario.getDocumentos().clear();
            }
            Usuario usuarioSalvo = usuarioRepository.save(usuario);
            if (!documentos.isEmpty()) {
                documentos.forEach(doc -> doc.setUser(usuarioSalvo));
                usuarioSalvo.getDocumentos().addAll(documentosRepository.saveAll(documentos));
            }
            if (!dependente.isEmpty()) {
                dependente.forEach(dep -> dep.setUser(usuarioSalvo));
                usuarioSalvo.getDependente().addAll(dependenteRepository.saveAll(dependente));
            }

            return usuarioSalvo;
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