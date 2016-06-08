/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.model.trabalhos.avaliacoes;

/**
 *
 * @author csiqueira
 */
public class AvaliacaoAgregada {
	private int totalPontos;
	private int totalAvaliacoes;

	public AvaliacaoAgregada() {
		totalPontos = 0;
		totalAvaliacoes = 0;
	}
	
	public int getTotalPontos() {
		return totalPontos;
	}

	public void setTotalPontos(int totalPontos) {
		this.totalPontos = totalPontos;
	}

	public int getTotalAvaliacoes() {
		return totalAvaliacoes;
	}

	public void setTotalAvaliacoes(int totalAvaliacoes) {
		this.totalAvaliacoes = totalAvaliacoes;
	}
}
