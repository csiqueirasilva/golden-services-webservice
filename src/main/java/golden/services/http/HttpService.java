/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class HttpService {

    public final static class Mappings {

        public final static String USUARIO_LOGIN = "usuarios/login";
        public final static String USUARIO_LOGOUT = "usuarios/logout";
        public final static String USUARIO_CRIAR = "usuarios/criar";
        public final static String USUARIO_ATIVAR = "usuarios/ativar";
        public final static String USUARIO_LISTAR = "usuarios/list";
        public final static String USUARIO_CURRENT = "usuarios/current";

        public final static String ANUNCIO_CRIAR = "anuncios/criar";
        public final static String ANUNCIO_OBTER = "anuncios/obter";
        public final static String ANUNCIO_LISTAR = "anuncios/listar";
		
        public final static String TRABALHO_CRIAR = "trabalhos/criar";
        public final static String TRABALHO_LISTAR_PRESTADOR = "trabalhos/listar";
        public final static String TRABALHO_LISTAR_CLIENTE = "trabalhos/listar-nao-avaliados";
        public final static String TRABALHO_ATUAL = "trabalhos/atual";
        public final static String TRABALHO_CANCELAR = "trabalhos/cancelar";
        public final static String TRABALHO_ENCERRAR = "trabalhos/encerrar";
        public final static String TRABALHO_CONFIRMAR = "trabalhos/confirmar";
        public final static String TRABALHO_NEGAR = "trabalhos/negar";
        public final static String TRABALHO_OBTER = "trabalhos/obter";

		public final static String AVALIACAO_CRIAR = "avaliacoes/criar";
		
    };

    //private final String baseUrl = "http://localhost:8084/GSWebservice/";
	private final String baseUrl = "http://daed-dev.on.br/GSWebservice/";

    private final HttpClient client = HttpClients.createDefault();

    public String getData(String url, String... args) {
        List<BasicNameValuePair> params = new ArrayList();

        if (args.length > 1) {
            for (int i = 0; i < args.length; i = i + 2) {
                params.add(new BasicNameValuePair(args[i], args[i + 1]));
            }
        }

        return getData(url, params);
    }

    public <T extends Object> T getData(String url, Class<T> cls, String... args) {

        String json = getData(url, args);

        T ret = null;

        try {
            ret = new ObjectMapper().readValue(json, cls);
        } catch (IOException ex) {
            Logger.getLogger(HttpService.class.getName()).log(Level.WARNING, null, ex);
        }

        return ret;
    }

    public String getData(String url, List<BasicNameValuePair> params) {

        String ret = null;

        try {

            HttpPost httppost = new HttpPost(baseUrl + url);

            // Request parameters and other properties.
            httppost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

            //Execute and get the response.
            HttpResponse response = client.execute(httppost);
            HttpEntity entity = response.getEntity();

            if (entity != null) {
                try (InputStream instream = entity.getContent()) {
                    Scanner s = new Scanner(instream).useDelimiter("\\A");
                    String rawJson = s.hasNext() ? s.next() : "";
                    if (rawJson.isEmpty()) {
                        ret = "null";
                    } else {
                        ret = rawJson;
                    }
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(HttpService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return ret;
    }

}
