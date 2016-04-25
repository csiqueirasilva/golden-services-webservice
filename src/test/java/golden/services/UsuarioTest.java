package golden.services;

import golden.services.http.HttpService;
import golden.services.model.usuarios.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.Assert;
import org.apache.http.message.BasicNameValuePair;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

/**
 *
 * @author csiqueira
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader=AnnotationConfigContextLoader.class, classes = {HttpService.class})
@Configurable
public class UsuarioTest {

	@Autowired
	private HttpService http;
	
	@Test
	public void criar() {
		
		String emailUsuario = System.currentTimeMillis() + "a@a.com";
		String passwordUsuario = "a";
		
		Usuario usuarioCriado = http.getData(HttpService.Mappings.USUARIO_CRIAR, Usuario.class, "email", emailUsuario, "password", passwordUsuario, "nome", "a", "endereco", "", "telefone", "", "sexo", "", "sobre", "");
		
		String hashAtivo = usuarioCriado.getHashAtivo();
		String idUsuario = usuarioCriado.getId().toString();
		
		Usuario usuarioConfirmado = http.getData(HttpService.Mappings.USUARIO_ATIVAR, Usuario.class, "id", idUsuario, "hash", hashAtivo);
		
		Usuario usuarioLogado = http.getData(HttpService.Mappings.USUARIO_LOGIN, Usuario.class, "username", usuarioConfirmado.getEmail(), "password", passwordUsuario);

		String listaUsuarios = http.getData(HttpService.Mappings.USUARIO_LISTAR);
		System.out.println(listaUsuarios);
		
		Object ret = http.getData(HttpService.Mappings.USUARIO_LOGOUT);
		System.out.println(ret);
	}
	
}