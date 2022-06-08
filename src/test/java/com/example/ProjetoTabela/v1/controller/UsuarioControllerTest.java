package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.v1.service.UsuarioService;
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
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    UsuarioService usuarioService;

    UsuarioController usuarioController;

    @Autowired
    MockMvc mockMvc;

    @Before
    public void init() {
        usuarioService = mock(UsuarioService.class);
        usuarioController = new UsuarioController(usuarioService);
    }

    @Test
    public void testfindAll() throws Exception {
        Usuario usuario = new Usuario(1L, "lua", "silva", "luna@gmail.com", 30, true);
        List<Usuario> userList = List.of(usuario);
        Mockito.when(usuarioService.retrieve(1L)).thenReturn(usuario);
        assertEquals(usuarioService.retrieve(usuario.getId()), usuario);
    }

    @Test
    public void criarUsuarioTest() {
        Usuario usuarioParaCriar = new Usuario(null, "matheus", "bernardes", "bernardes@email.com", 22, false);
        Usuario usuarioCriado = new Usuario(1L, "matheus", "bernardes", "bernardes@email.com", 22, true);
        when(usuarioService.save(usuarioParaCriar)).thenReturn(usuarioCriado);

        Assert.assertEquals(usuarioController.C(usuarioParaCriar).getNome(), usuarioCriado.getNome());
        Assert.assertEquals(usuarioController.C(usuarioParaCriar).getSobrenome(), usuarioCriado.getSobrenome());
        Assert.assertEquals(usuarioController.C(usuarioParaCriar).getEmail(), usuarioCriado.getEmail());
        Assert.assertEquals(usuarioController.C(usuarioParaCriar).getIdade(), usuarioCriado.getIdade());
        Assert.assertTrue(usuarioController.C(usuarioParaCriar).isAtivo());
    }

    private RequestBuilder get(String s) {
        return null;
    }

}


