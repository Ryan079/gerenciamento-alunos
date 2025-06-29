package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceUsuario serviceUsuario;

    @Test
    public void testeGetLogin() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testeGetCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"));
    }

    @Test
    public void testePostCadastrarUsuario() throws Exception {
        Usuario u = new Usuario();
        u.setEmail("emailqualquer@gmail.com");
        u.setSenha("senhaqualquer");
        u.setUser("usuário qualquer");

        mockMvc.perform(post("/salvarUsuario")
                .param("user", u.getUser())
                .param("senha", u.getSenha())
                .param("email", u.getEmail()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(serviceUsuario).salvarUsuario(any(Usuario.class));
    }

    @Test
    public void testeLoginPorUsuarioSenha() throws Exception {
        Usuario u = new Usuario();
        u.setEmail("emailaleatorio@gmail.com");
        u.setSenha("senhaaleatoria");
        u.setUser("usuário aleatório");

        when(serviceUsuario.loginUser(eq("usuário aleatório"), anyString()))
                .thenReturn(u);

        mockMvc.perform(post("/login")
                        .param("user", "usuário aleatório")
                        .param("senha", "senhaaleatoria")
                        .param("email", "emailaleatorio@gmail.com"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"));
    }
}
