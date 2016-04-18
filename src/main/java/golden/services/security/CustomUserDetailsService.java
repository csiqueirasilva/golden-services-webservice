/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package golden.services.security;

import golden.services.model.usuarios.UsuarioDLO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

	@Autowired
	private UsuarioDLO users;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return users.getUserDetails(email);
	}
	
}