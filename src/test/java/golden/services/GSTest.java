package golden.services;

import golden.services.http.ConnectorWebService;
import golden.services.http.HttpService;
import golden.services.model.usuarios.ListaUsuarios;
import golden.services.model.usuarios.Usuario;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
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

	private Usuario criarUsuario() {
		int rng = (int) (Math.random() * 1000);
		String emailUsuario = rng + System.currentTimeMillis() + "a@a.com";
		return ConnectorWebService.criarUsuario(emailUsuario, PASSWORD_USUARIO, "", "", "", "", "");
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
	public void Test0200_criarTrabalho() {

	}

}
