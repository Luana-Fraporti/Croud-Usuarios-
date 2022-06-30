package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.v1.service.DocumentosUsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/documentos")
public class DocumentosUsuarioController {


    final DocumentosUsuarioService documentosUsuarioService;

    @PostMapping
    @ResponseBody
    public Documentos C(@RequestBody Documentos doc) {
        return documentosUsuarioService.save(doc);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public Documentos R(@PathVariable Long id) {
        return documentosUsuarioService.retrieve(id);
    }

    @PutMapping
    @ResponseBody
    public Documentos U(@Valid @RequestBody Documentos doc) {
        return documentosUsuarioService.update(doc);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void D(@PathVariable Long id) {
        documentosUsuarioService.delete(id);
    }

}