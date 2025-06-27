package br.com.gerenciamento.service;

import br.com.gerenciamento.model.Usuario;
import jakarta.servlet.ServletConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Test
    public void loginBemSucedido() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUser("Ryan");
        usuario.setSenha("senha987");
        usuario.setEmail("ryan2@gmail.com");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), usuario.getSenha());

        Assert.assertEquals(usuario.getEmail(),usuarioLogado.getEmail());

    }

    @Test
    public void loginUsuarioInexistente() {

        Assert.assertNull(this.serviceUsuario.loginUser("Ryan", "Senha"));
    }

    @Test
    public void loginSenhaIncorreta() throws Exception{
        Usuario usuario = new Usuario();
        usuario.setUser("Ryan");
        usuario.setSenha("senha1234");
        usuario.setEmail("ryan@gmail.com");

        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioLogado = serviceUsuario.loginUser(usuario.getUser(), "SenhaQualquer");

        Assert.assertNull(usuarioLogado);

    }

    @Test
    public void salvarUsuarioNomeLongo() {
        Usuario usuario = new Usuario();
        usuario.setUser("Usuario realmente com nome muito grande mesmo");
        usuario.setSenha("senha1234");
        usuario.setEmail("emaildeusuariodenomemuitogrande@gmail.com");

        Assert.assertThrows(Exception.class, () -> {
            this.serviceUsuario.salvarUsuario(usuario);
        });
    }
}
