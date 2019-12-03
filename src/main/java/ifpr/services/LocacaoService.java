package ifpr.services;

import ifpr.models.Filme;
import ifpr.models.Locacao;
import ifpr.models.Usuario;
import ifpr.utils.DataUtils;

import java.util.ArrayList;
import java.util.Date;

public class LocacaoService {

    public Locacao alugarFilme(Usuario usuario, ArrayList<Filme> filmes) throws Exception {

        Locacao locacao = new Locacao();
        locacao.setUsuario(usuario);
        Double valor = 0.0;
        for(int i=0; i<filmes.size(); i++) {
            Filme filme = filmes.get(i);
            valor += filme.getPreco();
            if(filme.getEstoque() == 0) {
                filmes.remove(filme);
                throw new Exception("Filme não possui estoque mínimo!");
            }
        }

        Date data = new Date();

        locacao.setFilme(filmes);
        locacao.setDataLocacao(new Date());
        locacao.setDataDevolucao(DataUtils.adicionarDias(new Date(), 1));

        if(DataUtils.verificaDomingo(locacao.getDataDevolucao())) {
            locacao.setDataDevolucao(DataUtils.adicionarDias(new Date(), 2));
            throw new Exception("Não abre aos domingos!");
        }

        locacao.setValor(valor);
        return locacao;
    }

    public ArrayList<Filme> adicionarDesconto(ArrayList<Filme> filmes) throws Exception {
        if(filmes.size() >=3) {
            for(int i = 2; i < filmes.size(); i++) {
                Filme filme = filmes.get(i);

                if(i == 2) {
                    double newValor = filme.getPreco() - (0.25 * filme.getPreco());
                    filme.adicionarDesconto(newValor);
                }
                if(i == 3) {
                    double newValor = filme.getPreco() - (0.5 * filme.getPreco());
                    filme.adicionarDesconto(newValor);
                }
                if(i == 4) {
                    double newValor = filme.getPreco() - (0.75 * filme.getPreco());
                    filme.adicionarDesconto(newValor);
                }
                if(i == 5) {
                    double newValor = filme.getPreco() - filme.getPreco();
                    filme.adicionarDesconto(newValor);
                }
            }
        } else {
            throw new Exception("Não há filmes para adicionar desconto!");
        }
        return filmes;
    }

}
