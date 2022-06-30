package com.example.ProjetoTabela.v1.controller;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.domain.models.OrgaoDeExpedicao;
import com.example.ProjetoTabela.domain.models.enums.EnumTipoDocumento;
import com.example.ProjetoTabela.domain.repository.DocumentosRepository;
import com.example.ProjetoTabela.v1.service.DocumentosUsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DocumentosUsuarioControllerTest {
    @Mock
    DocumentosUsuarioService documentosUsuarioService;
    @Mock
    DocumentosRepository documentosRepository;
    @Mock
    DocumentosUsuarioController documentosUsuarioController;

    @Autowired
    MockMvc mockMvc;
    private ObjectMapper mapper;

    @Before
    public void init() {
        documentosUsuarioService = mock(DocumentosUsuarioService.class);
        documentosRepository = mock(DocumentosRepository.class);
        documentosUsuarioController = new DocumentosUsuarioController(documentosUsuarioService);
    }

    @Test
    public void testfindById() {
        LocalDate dataDeExpedicao = LocalDate.of(1991, 12, 13);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SSP");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "5106127195", dataDeExpedicao, orgaoDeExpedicao, "Larissa");
        Mockito.when(documentosUsuarioService.retrieve(1L)).thenReturn(documentos);
        assertEquals(documentosUsuarioController.R(documentos.getId()), documentos);
    }

    @Test
    public void criarDocumentosTest() {
        LocalDate dataDeExpedicao = LocalDate.of(1991, 12, 13);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SSP");
        Documentos documentosParaCriar = new Documentos(1L, EnumTipoDocumento.RG, "5106127195", dataDeExpedicao, orgaoDeExpedicao, "Larissa");
        Documentos documentosCriado = new Documentos(1L, EnumTipoDocumento.RG, "51006127", dataDeExpedicao, orgaoDeExpedicao, "Luana");
        when(documentosUsuarioService.save(documentosParaCriar)).thenReturn(documentosCriado);

        Documentos controllerCreateDocumentos = documentosUsuarioController.C(documentosParaCriar);
        Assert.assertEquals(controllerCreateDocumentos.getTipoDeDocumento(), documentosCriado.getTipoDeDocumento());
        Assert.assertEquals(controllerCreateDocumentos.getNumero(), documentosCriado.getNumero());
        Assert.assertEquals(controllerCreateDocumentos.getDataDeExpedicao(), documentosCriado.getDataDeExpedicao());
        Assert.assertEquals(controllerCreateDocumentos.getOrgaoExpedicao(), documentosCriado.getOrgaoExpedicao());
        Assert.assertEquals(controllerCreateDocumentos.getUser(), documentosCriado.getUser());
    }

    @Test
    public void deleteDocumentosTest() {
        LocalDate dataDeExpedicao = LocalDate.of(1991, 12, 13);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SSP");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "5106127195", dataDeExpedicao, orgaoDeExpedicao, "Larissa");
        when(documentosRepository.findById(documentos.getId())).thenReturn(Optional.of(documentos));
        documentosUsuarioController.D(documentos.getId());
        verify(documentosUsuarioService, times(1)).delete(documentos.getId());
    }

    @Test
    public void updateDocumentosTest() {
        LocalDate dataDeExpedicao = LocalDate.of(1991, 12, 13);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SSP");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "5106127195", dataDeExpedicao, orgaoDeExpedicao, "Larissa");
        Documentos documentos1 = new Documentos(1L, EnumTipoDocumento.RG, "5106127195", dataDeExpedicao, orgaoDeExpedicao, "Larissa");
        when(documentosUsuarioService.update(documentos)).thenReturn(documentos1);
        Documentos usuarioSalvo = documentosUsuarioController.U(documentos);
        assertEquals(usuarioSalvo.getId(), documentos1.getId());
        assertEquals(usuarioSalvo.getTipoDeDocumento(), documentos1.getTipoDeDocumento());
        assertEquals(usuarioSalvo.getNumero(), documentos1.getNumero());
        assertEquals(usuarioSalvo.getDataDeExpedicao(), documentos1.getDataDeExpedicao());
        assertEquals(usuarioSalvo.getOrgaoExpedicao(), documentos1.getOrgaoExpedicao());
        assertEquals(usuarioSalvo.getUser(), documentos1.getUser());
    }
}


