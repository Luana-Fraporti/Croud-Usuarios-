package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Dependente;
import com.example.ProjetoTabela.domain.repository.DependenteRepository;
import com.example.ProjetoTabela.v1.service.DependentesUsuarioService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DependentesUsuarioControllerTest {
    @Mock
    DependentesUsuarioService dependentesUsuarioService;
    @Mock
    DependenteRepository dependenteRepository;
    @Autowired
    DependentesUsuarioController dependentesUsuarioController;

    @Autowired
    MockMvc mockMvc;


    @Before
    public void init() {
        dependentesUsuarioService = mock(DependentesUsuarioService.class);
        dependenteRepository = mock(DependenteRepository.class);
        dependentesUsuarioController = new DependentesUsuarioController(dependentesUsuarioService);
    }

    @Test
    public void testfindById() throws Exception {
        Dependente dependente = new Dependente(1L, "lua", "silva", "luna@gmail.com", 30, true, null, null);
        Mockito.when(dependentesUsuarioService.retrieve(1L)).thenReturn(dependente);
        assertEquals(dependentesUsuarioController.R(dependente.getId()), dependente);
    }

    @Test
    public void criarDenpenteTest() {
        Dependente depentendeParaCriar = new Dependente(null, "matheus", "bernardes", "bernardes@email.com", 22, true, null, null);
        Dependente dependenteCriado = new Dependente(1L, "matheus", "bernardes", "bernardes@email.com", 22, true, null, null);
        when(dependentesUsuarioService .save(depentendeParaCriar)).thenReturn(dependenteCriado);

        Dependente controllerCreateUsuario = dependentesUsuarioController.C(depentendeParaCriar);
        Assert.assertEquals(controllerCreateUsuario.getNome(), dependenteCriado.getNome());
        Assert.assertEquals(controllerCreateUsuario.getSobrenome(), dependenteCriado.getSobrenome());
        Assert.assertEquals(controllerCreateUsuario.getEmail(), dependenteCriado.getEmail());
        Assert.assertEquals(controllerCreateUsuario.getIdade(), dependenteCriado.getIdade());
        Assert.assertTrue(controllerCreateUsuario.isAtivo());
    }

    @Test
    public void deleteDependenteTest() {
        Dependente dependente = new Dependente(1L,"Alex", "kolenchiski", "alex@hotmail.com", 32, true);
        dependenteRepository.findById(dependente.getId());
        dependentesUsuarioController.D(dependente.getId());
        verify(dependentesUsuarioService, times(1)).delete(dependente.getId());
    }

    @Test
    public void updateDependenteTest() {
        Dependente dependente = new Dependente(1L, "Alex", "kolenchiski", "alex@hotmail.com", 32, true, null, null);
        Dependente dependente1 = new Dependente(1L, "Alice", "Silva", "alice@hotmail.com", 31, true, null, null);
        Dependente dependenteSalvo = dependentesUsuarioController.U(dependente);
        assertEquals(dependenteSalvo.getId(), dependente1.getId());
        assertEquals(dependenteSalvo.getNome(), dependente1.getNome());
        assertEquals(dependenteSalvo.getSobrenome(), dependente1.getSobrenome());
        assertEquals(dependenteSalvo.getEmail(), dependente1.getEmail());
        assertEquals(dependenteSalvo.getIdade(), dependente1.getIdade());
        Assert.assertTrue(dependenteSalvo.isAtivo());
    }
}



