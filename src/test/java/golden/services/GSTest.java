package golden.services;

import golden.services.http.ConnectorWebService;
import golden.services.http.HttpService;
import golden.services.model.usuarios.ListaUsuarios;
import golden.services.model.usuarios.Usuario;
import junit.framework.Assert;
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

    private final String passwordUsuario = "a";

    private Usuario criarUsuario() {
        int rng = (int) (Math.random() * 1000);
        String emailUsuario = rng + System.currentTimeMillis() + "a@a.com";
        return ConnectorWebService.criarUsuario(emailUsuario, passwordUsuario, "", "", "", "", "");
    }

    @Test
    public void criarUsuarioTest() {

        Object ret = ConnectorWebService.deslogarUsuario();
        System.out.println(ret);

        Usuario usuarioCriado1 = criarUsuario();

        //String hashAtivo = usuarioCriado.getHashAtivo();
        //String idUsuario = usuarioCriado.getId().toString();
        //Usuario usuarioConfirmado = ConnectorWebService.confirmarUsuario(idUsuario, hashAtivo);
        Usuario usuarioLogado1 = ConnectorWebService.logarUsuario(usuarioCriado1.getEmail(), passwordUsuario);

        ListaUsuarios listaUsuarios = ConnectorWebService.listarUsuarios();
        System.out.println(listaUsuarios);

        ret = ConnectorWebService.deslogarUsuario();
        System.out.println(ret);
    }

    @Test
    public void logarSeguidamente() throws Exception {

        Usuario usuarioPreviamenteLogado = ConnectorWebService.getUsuarioLogado();

        System.out.println(usuarioPreviamenteLogado);

        Assert.assertNull(usuarioPreviamenteLogado);

        Usuario u1 = criarUsuario();
        Usuario u2 = criarUsuario();
        ConnectorWebService.logarUsuario(u1.getEmail(), passwordUsuario);

        System.out.println("USUARIO LOGADO : " + u1.getEmail());

        ConnectorWebService.logarUsuario(u2.getEmail(), passwordUsuario);

        System.out.println("USUARIO LOGADO : " + u2.getEmail());

        String email = ConnectorWebService.getUsuarioLogado().getEmail();

        System.out.println("USUARIO LOGADO : " + email);
    }
    
}
