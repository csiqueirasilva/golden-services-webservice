package golden.services;

import golden.services.http.ConnectorWebService;
import golden.services.http.HttpService;
import golden.services.model.anuncios.Anuncio;
import golden.services.model.anuncios.ListaAnuncios;
import golden.services.model.anuncios.TipoServico;
import golden.services.model.trabalhos.ListaTrabalhos;
import golden.services.model.trabalhos.Trabalho;
import golden.services.model.trabalhos.avaliacoes.Avaliacao;
import golden.services.model.usuarios.ListaUsuarios;
import golden.services.model.usuarios.Usuario;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author csiqueira
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {HttpService.class})
@Configurable
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class GSTest {

	private static final String PASSWORD_USUARIO = "a";

	private static Usuario prestador;
	private static Usuario cliente;

	private static Anuncio anuncioReferencia;

	private static Trabalho trabalhoReferencia;

	@Rule
	public TestName name = new TestName();

	@Before
	public void antesDeTestar() {
		System.out.println(name.getMethodName());
	}

	private Usuario criarUsuario() {
		int rng = (int) (Math.random() * 1000);
		String emailUsuario = rng + System.currentTimeMillis() + "a@a.com";
		return ConnectorWebService.criarUsuario(emailUsuario, PASSWORD_USUARIO, "", "", "", "", "");
	}

	private void loginUsuarioCliente() {
		ConnectorWebService.deslogarUsuario();
		ConnectorWebService.logarUsuario(cliente.getEmail(), PASSWORD_USUARIO);
	}

	private void loginUsuarioPrestador() {
		ConnectorWebService.deslogarUsuario();
		ConnectorWebService.logarUsuario(prestador.getEmail(), PASSWORD_USUARIO);
	}

	@Test
	public void Test0001_criarUsuarioTest() throws Exception {
		Object ret = ConnectorWebService.deslogarUsuario();

		Assert.assertTrue(ret == null || ret.equals("null"));

		prestador = criarUsuario();

		Assert.assertNotNull(prestador);
	}

	@Test
	public void Test0002_criarUsuarioErroEmailJaExiste() throws Exception {
		Usuario erro = ConnectorWebService.criarUsuario(prestador.getEmail(), PASSWORD_USUARIO, "", "", "", "", "");
		Assert.assertNull(erro);
	}

	@Test
	public void Test0003_criarUsuarioErroEmailInvalido() throws Exception {
		Usuario erro = ConnectorWebService.criarUsuario("@.com", PASSWORD_USUARIO, "", "", "", "", "");
		Assert.assertNull(erro);
	}

	@Test
	public void Test0004_criarUsuarioClienteTest() throws Exception {
		cliente = criarUsuario();
		Assert.assertNotNull(cliente);
	}

	@Test
	public void Test0005_logarUsuario() throws Exception {
		Object ret = ConnectorWebService.logarUsuario(prestador.getEmail(), PASSWORD_USUARIO);

		Assert.assertTrue(ret == null || ret.equals("null"));

		Usuario usuarioLogado = ConnectorWebService.getUsuarioLogado();

		Assert.assertNotNull(usuarioLogado);
	}

	@Test
	public void Test0006_listUsuario() {
		ListaUsuarios listaUsuarios = ConnectorWebService.listarUsuarios();
		Assert.assertNotNull(listaUsuarios);
	}

	@Test
	public void Test0007_deslogarUsuario() {
		Usuario usuarioLogado = ConnectorWebService.getUsuarioLogado();

		Assert.assertTrue(usuarioLogado != null);

		Assert.assertTrue(usuarioLogado.getId().equals(prestador.getId()));

		Object ret = ConnectorWebService.deslogarUsuario();
		Assert.assertTrue(ret == null || ret.equals("null"));
	}

	@Test
	public void Test0100_logarSeguidamente() throws Exception {

		Usuario usuarioPreviamenteLogado = ConnectorWebService.getUsuarioLogado();

		Assert.assertTrue(usuarioPreviamenteLogado == null);

		Usuario u1 = criarUsuario();
		Usuario u2 = criarUsuario();

		String emailU2 = u2.getEmail();

		ConnectorWebService.logarUsuario(u1.getEmail(), PASSWORD_USUARIO);
		ConnectorWebService.logarUsuario(u2.getEmail(), PASSWORD_USUARIO);

		String email = ConnectorWebService.getUsuarioLogado().getEmail();

		Assert.assertTrue(email.equals(emailU2));
	}

	@Test
	public void Test0200_criarAnuncioGratuito() {
		ConnectorWebService.deslogarUsuario();
		ConnectorWebService.logarUsuario(cliente.getEmail(), PASSWORD_USUARIO);
		Anuncio criarAnuncio = ConnectorWebService.criarAnuncio("AREA DE ATUACAO", "DESCRICAO", "0.0", "REGIAO", TipoServico.GRATUITO.toString());
		Assert.assertNotNull(criarAnuncio);
		Assert.assertTrue(criarAnuncio.getPrestador().getId().equals(cliente.getId()));
	}

	@Test
	public void Test0201_criarAnuncioPago() {
		ConnectorWebService.deslogarUsuario();
		ConnectorWebService.logarUsuario(prestador.getEmail(), PASSWORD_USUARIO);
		anuncioReferencia = ConnectorWebService.criarAnuncio("AREA DE ATUACAO", "DESCRICAO", "2000.0", "REGIAO", TipoServico.PAGO.toString());
		Assert.assertNotNull(anuncioReferencia);
		Assert.assertTrue(anuncioReferencia.getPrestador().getId().equals(prestador.getId()));
	}

	@Test
	public void Test0202_listarAnuncios() {
		ConnectorWebService.deslogarUsuario();
		ConnectorWebService.logarUsuario(cliente.getEmail(), PASSWORD_USUARIO);
		ListaAnuncios listarAnuncio = ConnectorWebService.listarAnuncio();
		Assert.assertNotNull(listarAnuncio);
		Assert.assertTrue(listarAnuncio.getListaAnuncios().size() >= 2);
	}

	@Test
	public void Test0300_criarTrabalho() {
		Trabalho criarTrabalho = ConnectorWebService.criarTrabalho(anuncioReferencia.getId().toString());
		Assert.assertNotNull(criarTrabalho);
		Usuario prestadorTrabalho = criarTrabalho.getAnuncio().getPrestador();
		Assert.assertEquals(prestadorTrabalho.getId(), prestador.getId());
		Usuario clienteTrabalho = criarTrabalho.getUsuario();
		Assert.assertEquals(cliente.getId(), clienteTrabalho.getId());
	}

	@Test
	public void Test0301_obterTrabalhosEncerradosNaoAvaliadosVazio() {
		loginUsuarioCliente();
		ListaTrabalhos lista = ConnectorWebService.listarTrabalhoClienteNaoAvaliado();
		Assert.assertNotNull(lista);
		Assert.assertTrue(lista.getTrabalhos().isEmpty());
	}

	@Test
	public void Test0302_obterTrabalhosNaoIniciados() {
		loginUsuarioPrestador();
		ListaTrabalhos lista = ConnectorWebService.listarTrabalhoPrestador();
		Assert.assertNotNull(lista);
		Assert.assertEquals(1, lista.getTrabalhos().size());
		trabalhoReferencia = lista.getTrabalhos().get(0);
	}

	@Test
	public void Test0303_cancelarTrabalho() {
		loginUsuarioCliente();
		int nCancelados = ConnectorWebService.cancelarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertEquals(1, nCancelados);
	}

	@Test
	public void Test0304_falhaCancelarTrabalho() {
		int nCancelados = ConnectorWebService.cancelarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertEquals(0, nCancelados);
	}

	@Test
	public void Test0305_erroAoCancelarTrabalhoAlheio() {
		loginUsuarioCliente();
		trabalhoReferencia = ConnectorWebService.criarTrabalho(anuncioReferencia.getId().toString());
		loginUsuarioPrestador();
		int nCancelados = ConnectorWebService.cancelarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertEquals(0, nCancelados);
	}

	@Test
	public void Test0306_erroAoRecusarTrabalhoAlheio() {
		loginUsuarioCliente();
		Trabalho negarTrabalho = ConnectorWebService.negarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertNull(negarTrabalho);
	}

	@Test
	public void Test0307_recusarTrabalho() {
		loginUsuarioPrestador();
		Trabalho negarTrabalho = ConnectorWebService.negarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertNotNull(negarTrabalho);
	}

	@Test
	public void Test0308_requeueTrabalho() {
		loginUsuarioCliente();
		trabalhoReferencia = ConnectorWebService.criarTrabalho(anuncioReferencia.getId().toString());
		Assert.assertNotNull(trabalhoReferencia);
	}
	
	@Test
	public void Test0316_erroAoAceitarTrabalhoAlheio() {
		loginUsuarioCliente();
		Trabalho confirmarTrabalho = ConnectorWebService.confirmarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertNull(confirmarTrabalho);
	}

	@Test
	public void Test0317_aceitarTrabalho() {
		loginUsuarioPrestador();
		Trabalho confirmarTrabalho = ConnectorWebService.confirmarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertNotNull(confirmarTrabalho);
	}

	@Test
	public void Test0318_erroAoEncerrarTrabalho() {
		loginUsuarioCliente();
		Trabalho encerrarTrabalho = ConnectorWebService.encerrarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertNull(encerrarTrabalho);
	}

	@Test
	public void Test0319_encerrarTrabalho() {
		loginUsuarioPrestador();
		Trabalho encerrarTrabalho = ConnectorWebService.encerrarTrabalho(trabalhoReferencia.getId().toString());
		Assert.assertNotNull(encerrarTrabalho);
	}

	@Test
	public void Test0400_avaliarErradoTrabalho() {
		Avaliacao avaliarTrabalho = ConnectorWebService.avaliarTrabalho(trabalhoReferencia.getId().toString(), "AVALIACAO COMENTARIO", "5");
		Assert.assertNull(avaliarTrabalho);
	}

	@Test
	public void Test0401_avaliarCorretamenteTrabalho() {
		loginUsuarioCliente();
		Avaliacao avaliarTrabalho = ConnectorWebService.avaliarTrabalho(trabalhoReferencia.getId().toString(), "AVALIACAO COMENTARIO", "5");
		Assert.assertNotNull(avaliarTrabalho);
	}

	
	
}
