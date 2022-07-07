package com.example.ProjetoTabela.v1.service;


import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.domain.repository.DependenteRepository;
import com.example.ProjetoTabela.domain.repository.DocumentosRepository;
import com.example.ProjetoTabela.domain.repository.UsuarioRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import com.example.ProjetoTabela.v1.DTO.UsuarioDTO;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private DependenteRepository dependenteRepository;
    @Mock
    private DocumentosRepository documentosRepository;

    UsuarioService usuarioService;

    @Before
    public void init() {
        usuarioService = new UsuarioService(usuarioRepository,documentosRepository,dependenteRepository);
    }

    @Test
    public void testFindAllUsuario() {
        List<Usuario> list = new ArrayList<>();
        list.add(new Usuario("John", "John", "john@gmail.com", 30, "true"));
        list.add(new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true"));
        list.add(new Usuario("Steve", "Waugh", "steve@outlook.com.br", 35, "true"));
        Mockito.when(usuarioRepository.findAll()).thenReturn(list);
        assertEquals(3, usuarioService.findAll().size());
    }

    @Test
    public void testRetrieveUsuario() {
        Usuario usuario = new Usuario("Steve", "Waugh", "steve@outlook.com.br", 35, "true");
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        assertEquals(usuarioService.retrieve(usuario.getId()), usuario);
        //Criar instancia de usuario que sera usada
        //mockear chamada ao repositório que o serviço faz
        //realizar assertiva entre usuario retornado e usuario experado
    }

    @Test
    public void testUpdateUsuario() {
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Usuario usuario1 = new Usuario(1L, "John", "John", "john@gmail.com", 30, true, null, null);
        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario1);
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario1));
        UsuarioDTO usuarioSalvo = usuarioService.update(usuario);
        assertEquals(usuarioSalvo.getId(), usuario1.getId());
        assertEquals(usuarioSalvo.getNome(), usuario1.getNome());
        assertEquals(usuarioSalvo.getSobrenome(), usuario1.getSobrenome());
        assertEquals(usuarioSalvo.getEmail(), usuario1.getEmail());
        assertEquals(usuarioSalvo.getIdade(), usuario1.getIdade());
        Assert.assertTrue(usuarioSalvo.isAtivo());
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        usuarioService.delete(usuario.getId());
        verify(usuarioRepository, times(1)).deleteById(usuario.getId());
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        Usuario usuario1 = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario1);
        assertEquals(usuarioService.save(usuario), usuario1);
    }

    @Test
    public void testSaveUsuarioRuntimeException() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        Mockito.when(usuarioRepository.save(usuario)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> usuarioService.save(usuario));
    }

    @Test
    public void testUpdateUsuarioNaoEncontradoException() {
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        Mockito.when(usuarioRepository.findById(usuario1.getId())).thenThrow(new ApiException(HttpStatus.BAD_REQUEST, ""));
        assertThrows(ApiException.class, () -> usuarioService.update(usuario));
    }

    @Test
    public void testUpdateViolacaoDeIntegridadeDosDadosException() {
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(ApiException.class, () -> usuarioService.update(usuario));
    }

    @Test
    public void testUpdateRuntimeException() {
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        Mockito.when(usuarioRepository.findById(usuario1.getId())).thenReturn(Optional.of(usuario1));
        Mockito.when(usuarioRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(ApiException.class, () -> usuarioService.update(usuario1));
    }

    @Test
    public void testDeleteException() {
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        when(usuarioRepository.findById(usuario1.getId())).thenReturn(Optional.empty());
        assertThrows(ApiException.class, () -> usuarioService.delete(1L));
    }

    @Test
    public void testDeleteByID() {
        when(usuarioRepository.findById(anyLong())).thenReturn(Optional.of(new Usuario()));
        doThrow(new RuntimeException()).when(usuarioRepository).deleteById(anyLong());
        assertThrows(ApiException.class, () -> usuarioService.delete(1L));
    }
}


