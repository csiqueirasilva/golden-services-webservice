/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.model.trabalhos;

import golden.services.model.anuncios.Anuncio;
import golden.services.model.anuncios.AnuncioDLO;
import golden.services.model.trabalhos.avaliacoes.Avaliacao;
import golden.services.model.usuarios.Usuario;
import golden.services.model.usuarios.UsuarioDLO;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class TrabalhoDLO {

	@Autowired
	private TrabalhoDAO trabalhoDAO;

	@Autowired
	private AnuncioDLO anuncioDLO;

	@Autowired
	private UsuarioDLO usuarioDLO;

	public Trabalho createTrabalho(String idAnuncioString) {
		Trabalho t = null;

		try {

			Anuncio anuncio = anuncioDLO.getAnuncio(idAnuncioString);
			Usuario usuario = usuarioDLO.getCurrentUsuario();

			if (anuncio != null && usuario != null && !anuncio.getPrestador().getId().equals(usuario.getId())) {
				t = new Trabalho();

				t.setAnuncio(anuncio);
				t.setEstado(EstadoTrabalho.NAO_INICIADO);
				t.setUsuario(usuario);

				trabalhoDAO.saveAndFlush(t);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return t;
	}

	public int cancelTrabalho(String idTrabalhoString) {
		int countDeleted = 0;

		try {

			Long idTrabalho = Long.parseLong(idTrabalhoString);
			Usuario usuario = usuarioDLO.getCurrentUsuario();

			countDeleted = trabalhoDAO.deleteNaoIniciadoByIdAndUsuario(idTrabalho, usuario);
			trabalhoDAO.flush();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return countDeleted;
	}

	public Trabalho denyTrabalho(String idTrabalhoString) {
		Trabalho t = null;

		Long idTrabalho = Long.parseLong(idTrabalhoString);
		Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();

		if (usuarioLogado != null) {
			t = trabalhoDAO.findOne(idTrabalho);

			if (t != null && t.getEstado().equals(EstadoTrabalho.NAO_INICIADO)) {
				Usuario prestador = t.getAnuncio().getPrestador();

				if (prestador.getId().equals(usuarioLogado.getId())) {
					t.setEstado(EstadoTrabalho.NEGADO);
					trabalhoDAO.saveAndFlush(t);
				} else {
                    t = null;
                }

			} else {
				t = null;
			}
		}

		return t;
	}

	public Trabalho startTrabalho(String idTrabalhoString) {
		Trabalho t = null;

		Long idTrabalho = Long.parseLong(idTrabalhoString);
		Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();

		if (usuarioLogado != null) {
			t = trabalhoDAO.findOne(idTrabalho);

			if (t != null) {
				Usuario prestador = t.getAnuncio().getPrestador();

				if (prestador.getId().equals(usuarioLogado.getId())) {

					Usuario usuario = t.getUsuario(); // client

					List<Trabalho> trabalhosPrestador = trabalhoDAO.findByPrestadorEfetuando(prestador);
					List<Trabalho> trabalhosUsuario = trabalhoDAO.findByUsuarioEfetuando(usuario);

					if (trabalhosPrestador.isEmpty() && trabalhosUsuario.size() == 1 && trabalhosUsuario.get(0).getId().equals(t.getId())) {
						Date d = new Date();

						t.setDatainicio(d);
						t.setEstado(EstadoTrabalho.EFETUANDO);

						trabalhoDAO.saveAndFlush(t);
					} else {
						t = null;
					}

				} else {
					t = null;
				}
			}

		}

		return t;
	}

	public Trabalho endTrabalho(String idTrabalhoString) {
		Trabalho t = null;

		Long idTrabalho = Long.parseLong(idTrabalhoString);
		Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();

		if (usuarioLogado != null) {
			t = trabalhoDAO.findOne(idTrabalho);

			if (t != null) {
				Usuario prestador = t.getAnuncio().getPrestador();

				if (prestador.getId().equals(usuarioLogado.getId())) {

					List<Trabalho> trabalhosPrestador = trabalhoDAO.findByPrestadorEfetuando(prestador);

					if (trabalhosPrestador.size() == 1 && trabalhosPrestador.get(0).getId().equals(t.getId())) {
						
						t = trabalhosPrestador.get(0);

						Date d = new Date();

						t.setDatafim(d);
						t.setEstado(EstadoTrabalho.ENCERRADO);
						trabalhoDAO.saveAndFlush(t);
						
					} else {
						t = null;
					}

				} else {
					t = null;
				}
			}

		}

		return t;
	}

	public TrabalhoAtual getTrabalhoAtual() {
		Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();
		TrabalhoAtual t = null;

		if (usuarioLogado != null) {

			List<Trabalho> trabalhosPrestador = trabalhoDAO.findByPrestadorEfetuando(usuarioLogado);
			List<Trabalho> trabalhosUsuario = trabalhoDAO.findByUsuarioEfetuando(usuarioLogado);

			if (trabalhosPrestador.size() > 0 || trabalhosUsuario.size() > 0) {
				t = new TrabalhoAtual();

				if (trabalhosPrestador.size() > 0) {
					Trabalho trabalho = trabalhosPrestador.get(0);
					t.setTrabalho(trabalho);
					t.setPapel(PapelTrabalho.PRESTADOR);
				} else {
					Trabalho trabalho = trabalhosUsuario.get(0);
					t.setTrabalho(trabalho);
					t.setPapel(PapelTrabalho.USUARIO);
				}
			}
		}

		return t;
	}

	public ListaTrabalhos getTrabalhosPrestador() {
		ListaTrabalhos t = null;
		Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();
		if (usuarioLogado != null) {
			List<Trabalho> findByPrestador = trabalhoDAO.findByPrestador(usuarioLogado);
			t = new ListaTrabalhos();
			t.setTrabalhos(findByPrestador);
		}
		return t;
	}

	public ListaTrabalhos getTrabalhosUsuarioNaoAvaliado() {
		ListaTrabalhos t = null;
		Usuario usuarioLogado = usuarioDLO.getCurrentUsuario();
		if (usuarioLogado != null) {
			List<Trabalho> findByPrestador = trabalhoDAO.findByUsuarioEncerradoNaoAvaliado(usuarioLogado);
			t = new ListaTrabalhos();
			t.setTrabalhos(findByPrestador);
		}
		return t;
	}

	public Trabalho getTrabalhoById(String idTrabalho) {
		Trabalho t = null;

		try {
			Long id = Long.parseLong(idTrabalho);
			t = trabalhoDAO.getOne(id);
		} catch (Exception e) {
		}

		return t;
	}

	public Trabalho addAvaliacao(Long idTrabalho, Avaliacao a) {
		Trabalho t = trabalhoDAO.getOne(idTrabalho);

		try {
			if (t != null && a != null && t.getAvaliacao() == null) {
				t.setAvaliacao(a);
				trabalhoDAO.saveAndFlush(t);
			} else {
				t = null;
			}
		} catch (Exception e) {
			t = null;
		}

		return t;
	}

    public Trabalho obterTrabalho(String idTrabalho) {
        Trabalho ret = null;
        try {
            Long id = Long.parseLong(idTrabalho);
            ret = trabalhoDAO.findOne(id);
        } catch (Exception e) {
        }
        return ret;
    }

}