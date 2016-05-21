package golden.services.http;

import golden.services.model.anuncios.Anuncio;
import golden.services.model.anuncios.ListaAnuncios;
import golden.services.model.usuarios.ListaUsuarios;
import golden.services.model.usuarios.Usuario;
import org.springframework.stereotype.Service;

/**
 *
 * @author 20141102610
 */
@Service
public class ConnectorWebService {

    private static final HttpService HTTP_HANDLER = new HttpService();

    /* Not connected */
    public static Usuario criarUsuario(String email, String password, String nome, String endereco, String telefone, String sexo, String sobre) {
        return HTTP_HANDLER.getData(HttpService.Mappings.USUARIO_CRIAR, Usuario.class, "email", email, "password", password, "nome", nome, "endereco", endereco, "telefone", telefone, "sexo", sexo, "sobre", sobre);
    }

    public static Usuario confirmarUsuario(String id, String hash) {
        return HTTP_HANDLER.getData(HttpService.Mappings.USUARIO_ATIVAR, Usuario.class, "id", id, "hash", hash);
    }

    public static Usuario logarUsuario(String email, String password) {
        return HTTP_HANDLER.getData(HttpService.Mappings.USUARIO_LOGIN, Usuario.class, "username", email, "password", password);
    }

    public static Usuario getUsuarioLogado() {
        return HTTP_HANDLER.getData(HttpService.Mappings.USUARIO_CURRENT, Usuario.class);
    }

    /* Needs login */
    public static ListaUsuarios listarUsuarios() {
        return HTTP_HANDLER.getData(HttpService.Mappings.USUARIO_LISTAR, ListaUsuarios.class);
    }

    public static Object deslogarUsuario() {
        return HTTP_HANDLER.getData(HttpService.Mappings.USUARIO_LOGOUT);
    }

    public static Anuncio criarAnuncio(String areaDeAtuacao, String descricao, String preco, String regiao, String tipoServicoString) {
        return HTTP_HANDLER.getData(HttpService.Mappings.ANUNCIO_CRIAR, Anuncio.class, "areaDeAtuacao", areaDeAtuacao, "descricao", descricao, "preco", preco, "regiao", regiao, "tipoServicoString", tipoServicoString);
    }

    public static ListaAnuncios listarAnuncio() {
        return HTTP_HANDLER.getData(HttpService.Mappings.ANUNCIO_LISTAR, ListaAnuncios.class);
    }

    public static Anuncio obterAnuncio(String idAnuncio) {
        return HTTP_HANDLER.getData(HttpService.Mappings.ANUNCIO_OBTER, Anuncio.class, "id", idAnuncio);
    }

}
