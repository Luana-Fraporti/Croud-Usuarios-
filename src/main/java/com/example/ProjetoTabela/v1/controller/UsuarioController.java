package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.v1.DTO.UsuarioDTO;
import com.example.ProjetoTabela.v1.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/usuario")
public class UsuarioController {

    final UsuarioService usuarioService;

    @PostMapping
    @ResponseBody
    public Usuario C(@RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UsuarioDTO R(@PathVariable Long id) {
            return usuarioService.retrieve(id);
    }

    @PutMapping
    @ResponseBody
    public Usuario U(@Valid @RequestBody Usuario usuario) {
        return usuarioService.update(usuario);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void D(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}