package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AlunoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceAluno serviceAluno;

    @Test
    public void testeGetInsertAlunos() throws Exception {
        mockMvc.perform(get("/inserirAlunos"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/formAluno"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void testePostInserirAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                        .param("nome", "Josué")
                        .param("matricula", "12634745734")
                        .param("status", "ATIVO")
                        .param("turno", "NOTURNO")
                        .param("curso", "INFORMATICA"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void testeListarTodosOsAlunos() throws Exception {
        when(serviceAluno.findAll()).thenReturn(List.of(new Aluno()));

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"))
                .andExpect(model().attributeExists("alunosList"));
    }

    @Test
    public void testePesquisarAluno() throws Exception {
        when(serviceAluno.findByNomeContainingIgnoreCase("ana"))
                .thenReturn(List.of(new Aluno()));

        mockMvc.perform(post("/pesquisar-aluno")
                .param("nome", "ana"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/pesquisa-resultado"))
                .andExpect(model().attributeExists("ListaDeAlunos"));
    }
}
