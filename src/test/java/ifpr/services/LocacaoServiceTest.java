package ifpr.services;

import ifpr.models.Filme;
import ifpr.models.Locacao;
import ifpr.models.Usuario;
import org.hamcrest.CoreMatchers;
import org.junit.*;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.Date;

import static ifpr.utils.DataUtils.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.*;

public class LocacaoServiceTest {
    Usuario usuario;
    LocacaoService service;

    @Rule
    public ErrorCollector error = new ErrorCollector();

    @Rule
    public ExpectedException expected;

    @Before
    public void setup(){
        /*é executado antes de cada método de teste*/
        System.out.println("before");
        usuario = new Usuario("Ricardo");
        service = new LocacaoService();
    }

    @After
    public void tearDown(){
        /*é executado após de cada método de teste*/
        System.out.println("after");
    }

    @Test
    public void testeLocacao(){
        ArrayList<Filme> filmes = new ArrayList<>();
        //cenario
        Filme filme0 = new Filme("Coringa", 10.0, 2);
        Filme filme1 = new Filme("Seis vezes confusão", 10.0, 2);
        Filme filme2 = new Filme("Shaft", 10.0, 2);
        Filme filme3 = new Filme("Operação Fronteira", 10.0, 2);
        Filme filme4 = new Filme("Bird Box", 10.0, 2);

        filmes.add(filme0);
        filmes.add(filme1);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);

        //acao
        Locacao locacao = null;
        try {
            locacao = service.alugarFilme(usuario, filmes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //verificação
        error.checkThat(locacao.getValor(), is(5.0));
        error.checkThat(locacao.getValor(), is(not(99.0)));
        error.checkThat(isMesmaData(locacao.getDataLocacao(), new Date()), is(true));
        error.checkThat(isMesmaData(locacao.getDataDevolucao(), adicionarDias(new Date(), 1)), is(true));
        error.checkThat(verificaDomingo(locacao.getDataDevolucao()), CoreMatchers.equalTo(true));
    }

    @Test
    public void naoPodeAlugarFilmeSemEstoque(){

        //cenario
        Usuario usuario = new Usuario("Ricardo");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme0 = new Filme("Coringa", 10.0, 2);
        Filme filme1 = new Filme("Seis vezes confusão", 10.0, 2);
        Filme filme2 = new Filme("Shaft", 10.0, 2);
        Filme filme3 = new Filme("Operação Fronteira", 10.0, 2);
        Filme filme4 = new Filme("Bird Box", 10.0, 2);

        filmes.add(filme0);
        filmes.add(filme1);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);

        //acao
        Locacao locacao = null;
        try {
            service.alugarFilme(usuario, filmes);
            fail("Deveria lançar uma exceção!");
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Desculpe! Sem estoque deste filme."));
        }
    }

    @Test(expected = Exception.class)
    public void naoPodeAlugarFilmeSemEstoque2() throws Exception {

        //cenario
        Usuario usuario = new Usuario("Ricardo");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme0 = new Filme("Coringa", 10.0, 2);
        Filme filme1 = new Filme("Seis vezes confusão", 10.0, 2);
        Filme filme2 = new Filme("Shaft", 10.0, 2);
        Filme filme3 = new Filme("Operação Fronteira", 10.0, 2);
        Filme filme4 = new Filme("Bird Box", 10.0, 2);

        filmes.add(filme0);
        filmes.add(filme1);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void naoPodeAlugarFilmeSemEstoque3() throws Exception {

        //cenario
        Usuario usuario = new Usuario("Ricardo");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme0 = new Filme("Coringa", 10.0, 2);
        Filme filme1 = new Filme("Seis vezes confusão", 10.0, 2);
        Filme filme2 = new Filme("Shaft", 10.0, 2);
        Filme filme3 = new Filme("Operação Fronteira", 10.0, 2);
        Filme filme4 = new Filme("Bird Box", 10.0, 2);

        filmes.add(filme0);
        filmes.add(filme1);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);

        expected.expect(Exception.class);
        expected.expectMessage("Desculpe! Sem estoque deste filme.");

        //acao
        service.alugarFilme(usuario, filmes);
    }

    @Test
    public void adicionarDescontos() throws Exception {
        //cenario
        Usuario usuario = new Usuario("Ricardo");
        ArrayList<Filme> filmes = new ArrayList<>();

        Filme filme0 = new Filme("Coringa", 10.0, 2);
        Filme filme1 = new Filme("Seis vezes confusão", 10.0, 2);
        Filme filme2 = new Filme("Shaft", 10.0, 2);
        Filme filme3 = new Filme("Operação Fronteira", 10.0, 2);
        Filme filme4 = new Filme("Bird Box", 10.0, 2);

        filmes.add(filme0);
        filmes.add(filme1);
        filmes.add(filme2);
        filmes.add(filme3);
        filmes.add(filme4);

        try {
            filmes = service.adicionarDesconto(filmes);
            fail("Deveria lançar uma exceção!");
        } catch (Exception e) {
            assertTrue(e.getMessage().equals("Filme sem estoque no momento!"));
        } finally {
            for(int i = 0; i < filmes.size(); i++) {
                System.out.println("Filme = " + filmes.get(i).getNome());
                System.out.println("Preço = " + filmes.get(i).getPreco());
            }
        }
    }
}
