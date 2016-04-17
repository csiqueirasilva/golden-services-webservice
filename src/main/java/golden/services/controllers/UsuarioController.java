/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.controllers;

import golden.services.model.usuarios.Usuario;
import golden.services.model.usuarios.UsuarioDLO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author csiqueira
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioDLO usuarioDLO;

	@RequestMapping("/list")
	public List<Usuario> getList() {
		return usuarioDLO.list();
	}

	@RequestMapping("/count")
	public Integer getCount() {
		return usuarioDLO.list().size();
	}
	
}