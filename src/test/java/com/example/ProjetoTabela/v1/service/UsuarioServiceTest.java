package com.example.ProjetoTabela.v1.service;


import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.domain.repository.UsuarioRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import javax.persistence.Id;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class UsuarioServiceTest {
    @Mock
    private UsuarioRepository usuarioRepository;

    UsuarioService usuarioService;

    @Before
    public void init() {
        usuarioService = new UsuarioService(usuarioRepository);
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
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Usuario usuario1 = new Usuario(1L, "John", "John", "john@gmail.com", 30, true);
        //Mockeia o repositório para retornar o John quando tentar salvar o Alex
        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario1);
        //Mockeia o repositório para retornar o John quando a busca for feita pelo Id 1
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario1));
        //Testa o retorno do atualizar para o usuário Alex
        Usuario usuarioSalvo = usuarioService.update(usuario);
        //Nesse ponto, o usuarioSalvo é o John, pois foi mockeado o repositório para retornar o John quando salvasse o Alex
        assertEquals(usuarioSalvo, usuario1);
        assertEquals(usuarioSalvo.getId(), usuario1.getId());
        assertEquals(usuarioSalvo.getNome(), usuario1.getNome());
        assertEquals(usuarioSalvo.getSobrenome(), usuario1.getSobrenome());
        assertEquals(usuarioSalvo.getEmail(), usuario1.getEmail());
        assertEquals(usuarioSalvo.getIdade(), usuario1.getIdade());
        Assert.assertTrue(usuarioSalvo.isAtivo());
        //Criar instancia de usuario pre atualizacao
        //Criar instancia de usuario pos atualizacao
        //mockear chamada ao repositório que o serviço faz com usuario pre e retornar usuario pos
        //realizar assertiva entre usuario retornado e o usuario pos
    }

    @Test
    public void testDeleteUsuario() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        usuarioService.delete(usuario.getId());
        verify(usuarioRepository, times(1)).deleteById(usuario.getId());
        //mockear chamada ao repositório que o serviço faz
        //usar metodo verify para se certificar de que o delete esta sendo chamado
    }

    @Test
    public void testSaveUsuario() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        Usuario usuario1 = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Mockito.when(usuarioRepository.save(usuario)).thenReturn(usuario1);
        assertEquals(usuarioService.save(usuario), usuario1);
        //criar instancia de usuario nao salvo
        //criar instancia de usuario salvo
        //mockear chamada ao repositório que o serviço faz
        //verificar se usuario retornado e igual a usuario salvo
    }

    @Test
    public void testSaveUsuarioRuntimeException() {
        Usuario usuario = new Usuario("Alex", "kolenchiski", "alex@hotmail.com", 32, "true");
        Usuario usuario1 = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Mockito.when(usuarioRepository.save(usuario)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> usuarioService.save(usuario));
        //criar instancia de usuario nao salvo
        //criar instancia de usuario salvo
        //mockear chamada ao repositório que o serviço faz
        //verificar se usuario retornado e igual a usuario salvo
    }

    @Test
    public void testUpdateUsuarioNaoEncontradoException() {
        //mockear find by id para tomar erro
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true);
        Mockito.when(usuarioRepository.findById(usuario1.getId())).thenThrow(new ApiException(HttpStatus.BAD_REQUEST, ""));
        assertThrows(ApiException.class, () -> usuarioService.update(usuario));
    }

    @Test
    public void testUpdateViolacaoDeIntegridadeDosDadosException() {
        //mockear save para tomar erro de integridade
        Usuario usuario = new Usuario(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Mockito.when(usuarioRepository.findById(usuario.getId())).thenReturn(Optional.of(usuario));
        Mockito.when(usuarioRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(ApiException.class, () -> usuarioService.update(usuario));
    }

    @Test
    public void testUpdateRuntimeException() {
        //mockear save para tomar erro generico
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true);
        Mockito.when(usuarioRepository.findById(usuario1.getId())).thenReturn(Optional.of(usuario1));
        Mockito.when(usuarioRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(ApiException.class, () -> usuarioService.update(usuario1));
    }

    @Test
    public void testDeleteException() {
        Usuario usuario1 = new Usuario(1L, "Alice", "Silva", "alice@hotmail.com", 31, true);
        Mockito.when(usuarioRepository.findById(usuario1.getId())).thenReturn(Optional.ofNullable(null));
        assertThrows(ApiException.class, () -> usuarioService.delete(1L));
    }

    @Test
    public void testDeleteByID() {
        lenient().when(usuarioRepository.findById(anyLong())).thenReturn(Optional.ofNullable(null));
        doThrow(new RuntimeException()).when(usuarioRepository).deleteById(anyLong());
        assertThrows(ApiException.class, () -> usuarioService.delete(1L));

    }
}


