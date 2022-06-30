package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.domain.repository.UsuarioRepository;
import com.example.ProjetoTabela.v1.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    UsuarioService usuarioService;
    UsuarioRepository usuarioRepository;

    UsuarioController usuarioController;

    @Autowired
    MockMvc mockMvc;
    private ObjectMapper mapper;

    public UsuarioControllerTest(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Before
    public void init() {
        usuarioService = mock(UsuarioService.class);
        usuarioRepository = mock(UsuarioRepository.class);
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    public void testfindById() throws Exception {
        Usuario usuario = new Usuario(1L, "lua", "silva", "luna@gmail.com", 30, true, null, null);
        List<Usuario> userList = List.of(usuario);
        Mockito.when(usuarioService.retrieve(1L)).thenReturn(usuario);
        assertEquals(usuarioController.R(usuario.getId()), usuario);
    }

    @Test
    public void criarUsuarioTest() {
        Usuario usuarioParaCriar = new Usuario(null, "matheus", "bernardes", "bernardes@email.com", 22, false, null, null);
        Usuario usuarioCriado = new Usuario(1L, "matheus", "bernardes", "bernardes@email.com", 22, true, null, null);
        when(usuarioService.save(usuarioParaCriar)).thenReturn(usuarioCriado);

        Usuario controllerCreateUsuario = usuarioController.C(usuarioParaCriar);
        Assert.assertEquals(controllerCreateUsuario.getNome(), usuarioCriado.getNome());
        Assert.assertEquals(controllerCreateUsuario.getSobrenome(), usuarioCriado.getSobrenome());
        Assert.assertEquals(controllerCreateUsuario.getEmail(), usuarioCriado.getEmail());
        Assert.assertEquals(controllerCreateUsuario.getIdade(), usuarioCriado.getIdade());
        Assert.assertTrue(controllerCreateUsuario.isAtivo());
    }

    @Test
    public void deleteUsuarioTest() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        usuarioController.D(usuario.getId());
        verify(usuarioService, times(1)).delete(usuario.getId());
    }

    @Test
    public void updateUsuarioTest() {
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        when(usuarioService.update(usuario)).thenReturn(usuario1);
        Usuario usuarioSalvo = usuarioController.U(usuario);
        assertEquals(usuarioSalvo.getId(), usuario1.getId());
        assertEquals(usuarioSalvo.getNome(), usuario1.getNome());
        assertEquals(usuarioSalvo.getSobrenome(), usuario1.getSobrenome());
        assertEquals(usuarioSalvo.getEmail(), usuario1.getEmail());
        assertEquals(usuarioSalvo.getIdade(), usuario1.getIdade());
        Assert.assertTrue(usuarioSalvo.isAtivo());
    }
}


