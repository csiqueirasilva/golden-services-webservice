/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.model.anuncios;

import golden.services.model.usuarios.Usuario;
import golden.services.model.usuarios.UsuarioDLO;
import java.math.BigDecimal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class AnuncioDLO {

    @Autowired
    private UsuarioDLO usuarioDLO;

    @Autowired
    private AnuncioDAO anuncioDAO;

    public Anuncio createAnuncio(String areaDeAtuacao, String descricao, String preco, String regiao, String tipoServicoString) {
        Usuario u = usuarioDLO.getCurrentUsuario();
        Anuncio a = null;

        try {

            if (u != null) {
                BigDecimal precoBigDecimal = BigDecimal.valueOf(Double.parseDouble(preco));
                a = new Anuncio();
                a.setAreaDeAtuacao(areaDeAtuacao);
                a.setDescricao(descricao);
                a.setPreco(precoBigDecimal);
                a.setPrestador(u);
                a.setRegiao(regiao);
                TipoServico tipoServico = TipoServico.valueOf(tipoServicoString);
                a.setTipoDeServico(tipoServico);
                anuncioDAO.saveAndFlush(a);
            }

        } catch (Exception e) {
        }

        return a;
    }

    public ListaAnuncios listAnuncio() {
        ListaAnuncios l = new ListaAnuncios();
        List<Anuncio> listaAnuncios = anuncioDAO.findAll();
        l.setListaAnuncios(listaAnuncios);
        return l;
    }

    public Anuncio getAnuncio(String idAnuncioString) {
        Anuncio a = null;

        try {
            Long id = Long.parseLong(idAnuncioString);
            a = anuncioDAO.findOne(id);
        } catch (Exception e) {
        }

        return a;
    }
}
