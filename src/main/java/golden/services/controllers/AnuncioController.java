/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.controllers;

import golden.services.model.anuncios.Anuncio;
import golden.services.model.anuncios.AnuncioDLO;
import golden.services.model.anuncios.ListaAnuncios;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 20141102610
 */
@RestController
@RequestMapping("/anuncios")
public class AnuncioController {

    @Autowired
    AnuncioDLO anuncioDLO;

    @RequestMapping("/criar")
    public Anuncio createAnuncio(@RequestParam String areaDeAtuacao, @RequestParam String descricao, @RequestParam String preco, @RequestParam String regiao, @RequestParam String tipoServicoString) {
        return anuncioDLO.createAnuncio(areaDeAtuacao, descricao, preco, regiao, tipoServicoString);
    }

    @RequestMapping("/listar")
    public ListaAnuncios listarAnuncio() {
        return anuncioDLO.listAnuncio();
    }

    @RequestMapping("/obter")
    public Anuncio obterAnuncio(@RequestParam String id) {
        return anuncioDLO.getAnuncio(id);
    }
    
    
}
