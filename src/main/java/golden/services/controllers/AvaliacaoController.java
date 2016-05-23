/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.controllers;

import golden.services.model.trabalhos.avaliacoes.Avaliacao;
import golden.services.model.trabalhos.avaliacoes.AvaliacaoDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 20141102610
 */
@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoController {

    @Autowired
    AvaliacaoDLO avaliacaoDLO;

    @RequestMapping("/criar")
    public Avaliacao createAvaliacao(@RequestParam String idTrabalho, @RequestParam String comentario, @RequestParam String nota) {
        return avaliacaoDLO.avaliarTrabalho(idTrabalho, comentario, nota);
    }
	
}