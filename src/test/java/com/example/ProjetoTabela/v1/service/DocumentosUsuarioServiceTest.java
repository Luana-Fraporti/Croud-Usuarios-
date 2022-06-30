package com.example.ProjetoTabela.v1.service;

import com.example.ProjetoTabela.domain.models.Documentos;
import com.example.ProjetoTabela.domain.models.OrgaoDeExpedicao;
import com.example.ProjetoTabela.domain.models.enums.EnumTipoDocumento;
import com.example.ProjetoTabela.domain.repository.DocumentosRepository;
import com.example.ProjetoTabela.exceptions.ApiException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class DocumentosUsuarioServiceTest {
    @Mock
    DocumentosRepository documentosRepository;
    @Mock
    DocumentosUsuarioService documentosUsuarioService;

    @Before
    public void init() {
        documentosUsuarioService = new DocumentosUsuarioService(documentosRepository);
    }


    @Test
    public void testFindAllDocumento() {
        List<Documentos> list = new ArrayList<>();
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        list.add(new Documentos(1L, EnumTipoDocumento.RG, "5106127195", dataDeExpedicao, orgaoDeExpedicao, "Larissa"));
        list.add(new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo"));
        list.add(new Documentos(1L, EnumTipoDocumento.CNH, "14524545116", dataDeExpedicao, orgaoDeExpedicao, "Fabiana"));
        Mockito.when(documentosRepository.findAll()).thenReturn(list);
        assertEquals(3, documentosUsuarioService.findAll().size());
    }

    @Test
    public void testRetrieveDocumento() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Mockito.when(documentosRepository.findById(documentos.getId())).thenReturn(Optional.of(documentos));
        assertEquals(documentosUsuarioService.retrieve(documentos.getId()), documentos);
        //Criar instancia de usuario que sera usada
        //mockear chamada ao repositório que o serviço faz
        //realizar assertiva entre usuario retornado e usuario experado
    }

    @Test
    public void testUpdateDocumento() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Documentos documentos1 = new Documentos(1L, EnumTipoDocumento.CNH, "14524545116", dataDeExpedicao, orgaoDeExpedicao, "Fabiana");
        //Mockeia o repositório para retornar o John quando tentar salvar o Alex
        Mockito.when(documentosRepository.save(documentos)).thenReturn(documentos1);
        //Mockeia o repositório para retornar o John quando a busca for feita pelo Id 1
        Mockito.when(documentosRepository.findById(documentos.getId())).thenReturn(Optional.of(documentos1));
        //Testa o retorno do atualizar para o usuário Alex
        Documentos documentosSalvo = documentosUsuarioService.update(documentos);
        //Nesse ponto, o usuarioSalvo é o John, pois foi mockeado o repositório para retornar o John quando salvasse o Alex
        assertEquals(documentosSalvo, documentos1);
        assertEquals(documentosSalvo.getId(), documentos1.getId());
        assertEquals(documentosSalvo.getTipoDeDocumento(), documentos1.getTipoDeDocumento());
        assertEquals(documentosSalvo.getNumero(), documentos1.getNumero());
        assertEquals(documentosSalvo.getDataDeExpedicao(), documentos1.getDataDeExpedicao());
        assertEquals(documentosSalvo.getOrgaoExpedicao(), documentos1.getOrgaoExpedicao());
        assertEquals(documentosSalvo.getUser(), documentos1.getUser());
        //Criar instancia de usuario pre atualizacao
        //Criar instancia de usuario pos atualizacao
        //mockear chamada ao repositório que o serviço faz com usuario pre e retornar usuario pos
        //realizar assertiva entre usuario retornado e o usuario pos
    }

    @Test
    public void testDeleteDocumento() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Mockito.when(documentosRepository.findById(documentos.getId())).thenReturn(Optional.of(documentos));
        documentosUsuarioService.delete(documentos.getId());
        verify(documentosRepository, times(1)).deleteById(documentos.getId());
        //mockear chamada ao repositório que o serviço faz
        //usar metodo verify para se certificar de que o delete esta sendo chamado
    }

    @Test
    public void testSaveDocumentos() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Documentos documentos1 = new Documentos(1L, EnumTipoDocumento.RG, "41522548471", dataDeExpedicao, orgaoDeExpedicao, "Luana");
        Mockito.when(documentosRepository.save(documentos)).thenReturn(documentos1);
        assertEquals(documentosUsuarioService.save(documentos), documentos1);
        //criar instancia de usuario nao salvo
        //criar instancia de usuario salvo
        //mockear chamada ao repositório que o serviço faz
        //verificar se usuario retornado e igual a usuario salvo
    }

    @Test
    public void testSaveDocumentosRuntimeException() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Mockito.when(documentosRepository.save(documentos)).thenThrow(new RuntimeException());
        assertThrows(RuntimeException.class, () -> documentosUsuarioService.save(documentos));
        //criar instancia de usuario nao salvo
        //criar instancia de usuario salvo
        //mockear chamada ao repositório que o serviço faz
        //verificar se usuario retornado e igual a usuario salvo
    }

    @Test
    public void testUpdateDocumentoNaoEncontradoException() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        //mockear find by id para tomar erro
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Documentos documentos1 = new Documentos(1L, EnumTipoDocumento.RG, "41522548471", dataDeExpedicao, orgaoDeExpedicao, "Luana");
        Mockito.when(documentosRepository.findById(documentos1.getId())).thenThrow(new ApiException(HttpStatus.BAD_REQUEST, ""));
        assertThrows(ApiException.class, () -> documentosUsuarioService.update(documentos));
    }

    @Test
    public void testUpdateViolacaoDeIntegridadeDosDadosException() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        //mockear save para tomar erro de integridade
        Documentos documentos = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Mockito.when(documentosRepository.findById(documentos.getId())).thenReturn(Optional.of(documentos));
        Mockito.when(documentosRepository.save(any())).thenThrow(new DataIntegrityViolationException(""));
        assertThrows(ApiException.class, () -> documentosUsuarioService.update(documentos));
    }

    @Test
    public void testUpdateRuntimeException() {
        LocalDate dataDeExpedicao = LocalDate.of(2005, 10, 25);
        OrgaoDeExpedicao orgaoDeExpedicao = new OrgaoDeExpedicao(12345L, "SJS");
        //mockear save para tomar erro generico
        Documentos documentos1 = new Documentos(1L, EnumTipoDocumento.RG, "2100516785", dataDeExpedicao, orgaoDeExpedicao, "Rodrigo");
        Mockito.when(documentosRepository.findById(documentos1.getId())).thenReturn(Optional.of(documentos1));
        Mockito.when(documentosRepository.save(any())).thenThrow(new RuntimeException());
        assertThrows(ApiException.class, () -> documentosUsuarioService.update(documentos1));
    }

    @Test
    public void testDeleteException() {
        assertThrows(ApiException.class, () -> documentosUsuarioService.delete(1L));
    }

    @Test
    public void testDeleteByID() {
        when(documentosRepository.findById(anyLong())).thenReturn(Optional.of(new Documentos()));
        doThrow(new RuntimeException()).when(documentosRepository).deleteById(anyLong());
        assertThrows(ApiException.class, () -> documentosUsuarioService.delete(1L));
    }
}
