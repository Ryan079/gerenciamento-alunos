package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void alterarStatusDoAlunoParaInativo() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Long id = aluno.getId();

        Aluno alunoRetorno = this.serviceAluno.getById(id);
        Assert.assertTrue(alunoRetorno.getStatus().equals(Status.ATIVO));

        aluno.setStatus(Status.INATIVO);
        this.serviceAluno.save(aluno);

        alunoRetorno = this.serviceAluno.getById(id);
        Assert.assertTrue(alunoRetorno.getStatus().equals(Status.INATIVO));
    }

    @Test
    public void apagarAluno() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Long id = aluno.getId();

        this.serviceAluno.deleteById(id);
        Assert.assertThrows(Exception.class, () -> {
            this.serviceAluno.getById(id);});
    }

    @Test
    public void salvarSemMatricula() {
        Aluno aluno = new Aluno();
        aluno.setNome("Renan");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);

        Assert.assertThrows(Exception.class, () -> {
            this.serviceAluno.save(aluno);
        });
    }

    @Test
    public void buscarAlunoNaoRegistrado() {

        Assert.assertThrows(Exception.class, () -> {
            this.serviceAluno.getById(45L);
        });
    }

}