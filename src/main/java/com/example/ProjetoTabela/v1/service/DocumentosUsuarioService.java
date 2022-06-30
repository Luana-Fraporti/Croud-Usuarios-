package com.example.ProjetoTabela.v1.service;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.domain.repository.DependenteRepository;
import com.example.ProjetoTabela.domain.repository.DocumentosRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DocumentosUsuarioService {
    DocumentosRepository documentosRepository;

    public List<Documentos> findAll() {
        return documentosRepository.findAll();
    }

    public Documentos update(Documentos doc) {
        return atualizarDocumentos(doc);
    }

    private Documentos atualizarDocumentos(Documentos doc) {
        documentosRepository.findById(doc.getId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,
                "Usuário não existe, não dá pra atualizar quem não existe"));
        try {
            return documentosRepository.save(doc);
        }
        //A ordem do catch deve seguir também a ordem de herança e polimorfismo
        catch (DataIntegrityViolationException dive) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não é possível salvar mais de um  documento do mesmo tipo");
        } catch (RuntimeException re) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Nao foi possivel atualizar o documento, entre em contato com o suporte");
        }
    }

    public Documentos retrieve(Long id) {
        return documentosRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Não há registros na base"));
    }

    public Documentos save(Documentos doc) {
        try {
            return documentosRepository.save(doc);
        } catch (RuntimeException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível salvar o documento");
        }
    }

    public void delete(Long id) {
        if (documentosRepository.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não existe documento na base com esse ID para deletar");
        }
        try {
            documentosRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível excluir o Documento de ID " + id);
        }
    }
}


