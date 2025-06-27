package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Before
    public void setUp() throws Exception {
        alunoRepository.deleteAll();
    }

    @Test
    public void encontrarAlunosAtivos() {
        Aluno a1 = new Aluno();
        a1.setNome("Aluno 1");
        a1.setTurno(Turno.MATUTINO);
        a1.setCurso(Curso.DIREITO);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("123456");
        alunoRepository.save(a1);

        Aluno a2 = new Aluno();
        a2.setNome("Aluno 2");
        a2.setTurno(Turno.NOTURNO);
        a2.setCurso(Curso.BIOMEDICINA);
        a2.setStatus(Status.ATIVO);
        a2.setMatricula("654321");
        alunoRepository.save(a2);

        List<Aluno> ativos = alunoRepository.findByStatusAtivo();
        Assert.assertEquals(2, ativos.size());

        for(Aluno aluno : ativos){
            Assert.assertEquals(Status.ATIVO, aluno.getStatus());
        }

    }
    @Test
    public void encontrarAlunosInativos() {
        Aluno a1 = new Aluno();
        a1.setNome("Josué");
        a1.setTurno(Turno.MATUTINO);
        a1.setCurso(Curso.DIREITO);
        a1.setStatus(Status.INATIVO);
        a1.setMatricula("321456");
        alunoRepository.save(a1);

        Aluno a2 = new Aluno();
        a2.setNome("Carlos");
        a2.setTurno(Turno.MATUTINO);
        a2.setCurso(Curso.INFORMATICA);
        a2.setStatus(Status.INATIVO);
        a2.setMatricula("456123");
        alunoRepository.save(a2);

        List<Aluno> inativos = alunoRepository.findByStatusInativo();
        Assert.assertEquals(2, inativos.size());

        for(Aluno aluno : inativos){
            Assert.assertEquals(Status.INATIVO, aluno.getStatus());
        }
    }

    @Test
    public void encontrarAlunoPorNomeIgnoreCase() {
        Aluno a1 = new Aluno();
        a1.setNome("Matheus");
        a1.setTurno(Turno.MATUTINO);
        a1.setCurso(Curso.DIREITO);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("162534");
        alunoRepository.save(a1);

        List<Aluno> encontrado = alunoRepository.findByNomeContainingIgnoreCase("Matheus");

        Assert.assertEquals(1, encontrado.size());
    }

    @Test
    public void encontrarAlunoPorNomeIgnoreCaseFalha() {
        Aluno a1 = new Aluno();
        a1.setNome("Matheus");
        a1.setTurno(Turno.MATUTINO);
        a1.setCurso(Curso.DIREITO);
        a1.setStatus(Status.ATIVO);
        a1.setMatricula("162534");
        alunoRepository.save(a1);

        List<Aluno> encontrado = alunoRepository.findByNomeContainingIgnoreCase("Renan");

        Assert.assertTrue(encontrado.isEmpty());
    }
}
