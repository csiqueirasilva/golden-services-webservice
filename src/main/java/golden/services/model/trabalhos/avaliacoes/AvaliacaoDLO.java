/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.model.trabalhos.avaliacoes;

import golden.services.model.anuncios.Anuncio;
import golden.services.model.anuncios.AnuncioDLO;
import golden.services.model.trabalhos.Trabalho;
import golden.services.model.trabalhos.TrabalhoDLO;
import golden.services.model.usuarios.Usuario;
import golden.services.model.usuarios.UsuarioDLO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class AvaliacaoDLO {
	
	@Autowired
	private AvaliacaoDAO dao;
	
	@Autowired
	private TrabalhoDLO trabalhoDLO;
	
	@Autowired
	private AnuncioDLO anuncioDLO;
	
	@Autowired
	private UsuarioDLO usuarioDLO;
	
	public Avaliacao avaliarTrabalho(String idTrabalho, String comentario, String nota) {
		
		Avaliacao a = null;
		Trabalho t = trabalhoDLO.getTrabalhoById(idTrabalho);
		Integer notaInt = null;
		
		try {
			notaInt = Integer.parseInt(nota);
			if(notaInt < 0) {
				notaInt = 0;
			} else if (notaInt > 5) {
				notaInt = 5;
			}
		} catch (Exception e) {
		}
		
		if(t != null && notaInt != null) {
			Usuario usuarioTrabalho = t.getUsuario();
			Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();
			if(usuarioLogado.getId().equals(usuarioTrabalho.getId())) {
				a = new Avaliacao();
				a.setComentario(comentario);
				a.setNota(notaInt);
				trabalhoDLO.addAvaliacao(t.getId(), a);
			}
		}
		
		return a;
	}
	
	public AvaliacaoAgregada agregarAvaliacao(String idAnuncio) {
		AvaliacaoAgregada ret = null;
		Anuncio anuncio = anuncioDLO.getAnuncio(idAnuncio);
		
		if(anuncio != null) {
			List<Avaliacao> avaliacoes = dao.findByAnuncio(anuncio);
			ret = new AvaliacaoAgregada();
			for(Avaliacao a : avaliacoes) {
				int totalAvaliacoes = ret.getTotalAvaliacoes() + 1;
				int totalNota = ret.getTotalPontos() + a.getNota();
				ret.setTotalAvaliacoes(totalAvaliacoes);
				ret.setTotalPontos(totalNota);
			}
		}
		
		return ret;
	}
}
