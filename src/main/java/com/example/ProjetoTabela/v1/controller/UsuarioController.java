package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.v1.DTO.UsuarioDTO;
import com.example.ProjetoTabela.v1.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

@Controller
@AllArgsConstructor
@RequestMapping(value = "/usuario")
public class UsuarioController {

    final UsuarioService usuarioService;

    @PostMapping
    @ResponseBody
    public UsuarioDTO C(@Valid @RequestBody Usuario usuario) {
        return usuarioService.save(usuario);
    }

    @GetMapping(value = "/{id}")
    @ResponseBody
    public UsuarioDTO R(@PathVariable Long id) {
            return usuarioService.retrieve(id);
    }

    @GetMapping(value = "/all")
    @ResponseBody
    public List<UsuarioDTO> RFindAllMapper() {
        return usuarioService.findAllMapper();
    }
    @GetMapping(value = "/mapper/{id}")
    @ResponseBody
    public UsuarioDTO RMapper(@PathVariable Long id) {
        return usuarioService.retrieveMapper(id);
    }
    @GetMapping(value = "/mapper")
    @ResponseBody
    public Set<UsuarioDTO> FindAllR() {
        return usuarioService.findAll();
    }
    @PutMapping
    @ResponseBody
    public UsuarioDTO U(@Valid @RequestBody Usuario usuario) {
        return usuarioService.update(usuario);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public void D(@PathVariable Long id) {
        usuarioService.delete(id);
    }

}