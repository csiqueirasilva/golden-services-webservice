package golden.services;

import golden.services.http.ConnectorWebService;
import golden.services.http.HttpService;
import golden.services.model.usuarios.ListaUsuarios;
import golden.services.model.usuarios.Usuario;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = {HttpService.class})
@Configurable
public class UsuarioTest {

    @Autowired
    private HttpService http;

    @Test
    public void criar() {

        String emailUsuario = System.currentTimeMillis() + "a@a.com";
        String passwordUsuario = "a";

        Usuario usuarioCriado = ConnectorWebService.criarUsuario(emailUsuario, passwordUsuario, "", "", "", "", "");

        String hashAtivo = usuarioCriado.getHashAtivo();
        String idUsuario = usuarioCriado.getId().toString();

        Usuario usuarioConfirmado = ConnectorWebService.confirmarUsuario(idUsuario, hashAtivo);

        Usuario usuarioLogado = ConnectorWebService.logarUsuario(usuarioConfirmado.getEmail(), passwordUsuario);

        ListaUsuarios listaUsuarios = ConnectorWebService.listarUsuarios();
        System.out.println(listaUsuarios);

        Object ret = ConnectorWebService.deslogarUsuario();
        System.out.println(ret);
    }

}
