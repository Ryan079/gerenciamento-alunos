package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Before
    public void setUp() throws Exception {
        usuarioRepository.deleteAll();
    }

    @Test
    public void encontrarPorEmail() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@gmail.com");
        usuario.setUser("Usuario1");
        usuario.setSenha("123456");

        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.findByEmail(usuario.getEmail());

        Assert.assertEquals(usuario.getEmail(), encontrado.getEmail());

    }

    @Test
    public void encontrarPorLogin() {
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario@gmail.com");
        usuario.setUser("Usuario1");
        usuario.setSenha("123456");
        usuarioRepository.save(usuario);

        Usuario encontrado = usuarioRepository.buscarLogin(usuario.getUser(), usuario.getSenha());

        Assert.assertEquals(usuario.getUser(), encontrado.getUser());
    }

    @Test
    public void encontrarPorEmailFalha() {

        Usuario u = usuarioRepository.findByEmail("usuario@gmail.com");
        Assert.assertNull(u);
    }
    
    @Test
    public void encontrarPorLoginFalha() {

        Usuario u = usuarioRepository.buscarLogin("UsuarioFalha", "sajdlkasjflsa");
        Assert.assertNull(u);
    }
}
