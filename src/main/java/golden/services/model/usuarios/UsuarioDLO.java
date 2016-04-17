/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.model.usuarios;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class UsuarioDLO {
	
	@Autowired
	private UsuarioDAO dao;
	
	public List<Usuario> list () {
		return dao.findAll();
	}
	
}
