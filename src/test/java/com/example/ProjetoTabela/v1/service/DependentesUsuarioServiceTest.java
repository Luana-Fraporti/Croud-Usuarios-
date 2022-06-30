package com.example.ProjetoTabela.v1.service;

import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.domain.models.Usuario;
import com.example.ProjetoTabela.domain.repository.DependenteRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DependentesUsuarioServiceTest {
    ////Ao testar uma classe devemos:
    /// identificar seu construtor, ou seja, quais objetos estão criados dentro dela e que precisam ser instanciados
    /// identificados os objetos, você precisa criar uma instância mockeada deles
    /// no caso desta classe, o DependentesUsuarioService depende de dois objetos para seu pleno funcionamento
    /// o repositório de documentos e o repositório de dependentes
    /// uma vez que você tenha todos os objetos criados e mockeados, você precisa então de um método anotado com a
    /// Annotation Before para inicializar sua classe que será testada.
    /// a inicialização da classe testada será nesse modelo abaixo:
    //// variavelDaClasseTestada = new ClasseTestada(ObjetoMockeado1,ObjetoMockeado2....);
    //// instanciado sua classe que será testada, os testes podem começar.
    @Mock
    private DependenteRepository dependenteRepository;
    DependentesUsuarioService dependentesUsuarioService;

    @Before
    public void setup() {
        dependentesUsuarioService = new DependentesUsuarioService(dependenteRepository);
    }


    @Test
    public void testFindAllDependente() {
        List<Dependente> list = new ArrayList<>();
        list.add(new Dependente(1L, "John", "John", "john@gmail.com", 30, true));
        list.add(new Dependente(2L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true));
        list.add(new Dependente(3L, "Steve", "Waugh", "steve@outlook.com.br", 35, true));
        Mockito.when(dependentesUsuarioService.findAll()).thenReturn(list);
        assertEquals(3, dependentesUsuarioService.findAll().size());
    }

    @Test
    public void testRetrieveDependente() {
        Dependente dependente = new Dependente(1L,"Steve", "Waugh", "steve@outlook.com.br", 35, true);
        Mockito.when(dependenteRepository.findById(dependente.getId())).thenReturn(Optional.of(dependente));
        assertEquals(dependentesUsuarioService.retrieve(dependente.getId()), dependente);
    }

    @Test
    public void testUpdateDependente() {
        Usuario user = new Usuario();
        Dependente dependente = new Dependente(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, user, null);
        Dependente dependente1 = new Dependente(1L, "John", "John", "john@gmail.com", 30, true, user, null);

        when(dependenteRepository.save(dependente)).thenReturn(dependente1);

        when(dependenteRepository.findById(dependente.getId())).thenReturn(Optional.of(dependente1));

        Dependente dependenteSalvo = dependentesUsuarioService.update(dependente);

        assertEquals(dependenteSalvo, dependente1);
        assertEquals(dependenteSalvo.getId(), dependente1.getId());
        assertEquals(dependenteSalvo.getNome(), dependente1.getNome());
        assertEquals(dependenteSalvo.getSobrenome(), dependente1.getSobrenome());
        assertEquals(dependenteSalvo.getEmail(), dependente1.getEmail());
        assertEquals(dependenteSalvo.getIdade(), dependente1.getIdade());
        assertTrue(dependenteSalvo.isAtivo());
        assertEquals(dependenteSalvo.getUser(), dependente1.getUser());
        assertEquals(dependenteSalvo.getTipoDeParentesco(), dependente1.getTipoDeParentesco());
    }

    @Test
    public void testDeleteDepentende() {
        Dependente dependente = new Dependente(1L,"Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Mockito.when(dependenteRepository.findById(dependente.getId())).thenReturn(Optional.of(dependente));
        dependentesUsuarioService.delete(dependente.getId());
        verify(dependenteRepository, times(1)).deleteById(dependente.getId());
    }

    @Test
    public void testSaveDepentende() {
        Dependente dependente = new Dependente(1L,"Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Dependente dependente1 = new Dependente(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Mockito.when(dependenteRepository.save(dependente)).thenReturn(dependente1);
        assertEquals(dependentesUsuarioService.save(dependente), dependente1);
    }

    @Test
    public void testSaveDependenteRuntimeException() {
        Dependente dependente = new Dependente(1L,"Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        Mockito.when(dependenteRepository.save(dependente)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> dependentesUsuarioService.save(dependente));
    }

    @Test
    public void testUpdateUsuarioNaoEncontradoException() {
        Dependente dependente = new Dependente(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        assertThrows(ApiException.class, () -> dependentesUsuarioService.update(dependente));
    }

    @Test
    public void testUpdateViolacaoDeIntegridadeDosDadosException() {
        Dependente dependente = new Dependente(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Mockito.when(dependenteRepository.findById(dependente.getId())).thenReturn(Optional.of(dependente));
        Mockito.when(dependenteRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(ApiException.class, () -> dependentesUsuarioService.update(dependente));
    }

    @Test
    public void testUpdateRuntimeException() {
        Dependente dependente1 = new Dependente(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        Mockito.when(dependenteRepository.findById(dependente1.getId())).thenReturn(Optional.of(dependente1));
        Mockito.when(dependenteRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(ApiException.class, () -> dependentesUsuarioService.update(dependente1));
    }

    @Test
    public void testDeleteException() {
        Dependente dependente = new Dependente(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        dependenteRepository.findById(dependente.getId());
        assertThrows(ApiException.class, () -> dependentesUsuarioService.delete(1L));
    }

    @Test
    public void testDeleteByID() {
        when(dependenteRepository.findById(anyLong())).thenReturn(Optional.of(new Dependente()));
        doThrow(new RuntimeException()).when(dependenteRepository).deleteById(anyLong());
        assertThrows(ApiException.class, () -> dependentesUsuarioService.delete(1L));
    }
}
