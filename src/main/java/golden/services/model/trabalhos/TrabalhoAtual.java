/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.model.trabalhos;

/**
 *
 * @author csiqueira
 */
public class TrabalhoAtual {
	
	private PapelTrabalho papel;
	
	private Trabalho trabalho;

	public Trabalho getTrabalho() {
		return trabalho;
	}

	public void setTrabalho(Trabalho trabalho) {
		this.trabalho = trabalho;
	}

	public PapelTrabalho getPapel() {
		return papel;
	}

	public void setPapel(PapelTrabalho papel) {
		this.papel = papel;
	}

}
