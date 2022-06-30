package com.example.ProjetoTabela.v1.service;


import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.domain.repository.DependenteRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class DependentesUsuarioService {
    DependenteRepository dependenteRepository;

    public List<Dependente> findAll() {
        return dependenteRepository.findAll();
    }

    public Dependente update(Dependente dep) {
        return atualizarDependente(dep);
    }


    private Dependente atualizarDependente(Dependente dep) {
        dependenteRepository.findById(dep.getId()).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST,
                "Usuário não existe, não dá pra atualizar quem não existe"));
        try {
            return dependenteRepository.save(dep);
        }
        //A ordem do catch deve seguir também a ordem de herança e polimorfismo
        catch (DataIntegrityViolationException dive) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não é possível salvar mais de um  documento do mesmo tipo");
        } catch (RuntimeException re) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Nao foi possivel atualizar o documento, entre em contato com o suporte");
        }
    }

    public Dependente retrieve(Long id) {
        return dependenteRepository.findById(id).orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Não há registros na base"));
    }

    public Dependente save(Dependente dep) {
        try {
            return dependenteRepository.save(dep);
        } catch (RuntimeException ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível salvar o dependente");
        }
    }

    public void delete(Long id) {
        if (dependenteRepository.findById(id).isEmpty()) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não existe dependente na base com esse ID para deletar");
        }
        try {
            dependenteRepository.deleteById(id);
        } catch (Exception ex) {
            throw new ApiException(HttpStatus.BAD_REQUEST, "Não foi possível excluir o Dependente de ID " + id);
        }
    }
}
