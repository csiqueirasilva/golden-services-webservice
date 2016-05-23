/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.controllers;

import golden.services.model.trabalhos.ListaTrabalhos;
import golden.services.model.trabalhos.Trabalho;
import golden.services.model.trabalhos.TrabalhoAtual;
import golden.services.model.trabalhos.TrabalhoDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author 20141102610
 */
@RestController
@RequestMapping("/trabalhos")
public class TrabalhoController {

	@Autowired
	TrabalhoDLO trabalhoDLO;

	@RequestMapping("/criar")
	public Trabalho criarTrabalho(@RequestParam String idAnuncio) {
		return trabalhoDLO.createTrabalho(idAnuncio);
	}

	@RequestMapping("/listar") /* prestador */
	public ListaTrabalhos listarTrabalho() {
		return trabalhoDLO.getTrabalhosPrestador();
	}

	@RequestMapping("/listar-nao-avaliados") /* cliente */
	public ListaTrabalhos listarTrabalhoNaoAvaliado() {
		return trabalhoDLO.getTrabalhosUsuarioNaoAvaliado();
	}

	@RequestMapping("/atual")
	public TrabalhoAtual obterTrabalhoAtual() {
		return trabalhoDLO.getTrabalhoAtual();
	}

	@RequestMapping("/cancelar") /* cliente */
	public int cancelarTrabalho(@RequestParam String idTrabalho) {
		return trabalhoDLO.cancelTrabalho(idTrabalho);
	}

	@RequestMapping("/encerrar") /* prestador */
	public Trabalho encerrarTrabalho(@RequestParam String idTrabalho) {
		return trabalhoDLO.endTrabalho(idTrabalho);
	}
	
	@RequestMapping("/confirmar") /* prestador */
	public Trabalho confirmarTrabalho(@RequestParam String idTrabalho) {
		return trabalhoDLO.startTrabalho(idTrabalho);
	}

    @RequestMapping("/negar") /* prestador */
	public Trabalho negarTrabalho(@RequestParam String idTrabalho) {
		return trabalhoDLO.denyTrabalho(idTrabalho);
	}

    @RequestMapping("/obter") /* prestador */
	public Trabalho obterTrabalho(@RequestParam String idTrabalho) {
		return trabalhoDLO.obterTrabalho(idTrabalho);
	}
    
}
