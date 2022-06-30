package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.v1.service.DependentesUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/dependentes")
public class DependentesUsuarioController {
    final DependentesUsuarioService dependentesUsuarioService;
    ///Data Transfer Object

    @PostMapping(value = "/documentos")
    @ResponseBody
    public Dependente C(@RequestBody Dependente dependente) {
        return dependentesUsuarioService.save(dependente);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Dependente R(@PathVariable Long id) {
        return dependentesUsuarioService.retrieve(id);
    }
    @GetMapping
    @ResponseBody
    public List<Dependente> rFindAll(){
        return dependentesUsuarioService.findAll();
    }




    @PutMapping
    @ResponseBody
    public Dependente U(@Valid @RequestBody Dependente dependente) {
        return dependentesUsuarioService.update(dependente);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void D(@PathVariable Long id) {
        dependentesUsuarioService.delete(id);
    }

}
