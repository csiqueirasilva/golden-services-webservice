package golden.services.model.usuarios;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author csiqueira
 */
public class CustomUserDetails implements UserDetails {

	private final Usuario usuario;

	public CustomUserDetails(Usuario u) {
		usuario = u;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		GrantedAuthority GA = new GrantedAuthority() {
			@Override
			public String getAuthority() {
				return "ROLE_USER";
			}
		};
		
		ArrayList list = new ArrayList<>();
		list.add(GA);
		return list;
	}

	@Override
	public String getPassword() {
		String ret = null;

		try {
			Field password = Usuario.class.getDeclaredField("password");
			password.setAccessible(true);

			ret = (String) password.get(usuario);

		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException ex) {
			Logger.getLogger(CustomUserDetails.class.getName()).log(Level.SEVERE, null, ex);
		}
		
		return ret;
	}

	@Override
	public String getUsername() {
		return usuario.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return usuario.getAtivo();
	}

}
