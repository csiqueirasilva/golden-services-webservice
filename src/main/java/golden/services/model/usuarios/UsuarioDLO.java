package golden.services.model.usuarios;

import java.text.ParseException;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author csiqueira
 */
@Service
public class UsuarioDLO {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UsuarioDAO dao;

	private static final String EMAIL_PATTERN_STRING
			= "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_PATTERN_STRING);

	public boolean validateEmail(final String email) {
		return EMAIL_PATTERN.matcher(email).matches();
	}

	public ListaUsuarios list() {
            List<Usuario> findAll = dao.findAll();
            ListaUsuarios ret = new ListaUsuarios();
            ret.setUsuarios(findAll);
            return ret;
	}

	public Usuario findByEmailAndPassword(String email, String password) {
		Usuario ret = null;
		Usuario user = dao.findByEmail(email);

		if (user != null) {

			String encrypted = user.getPassword();

			if (passwordEncoder.matches(password, encrypted)) {
				ret = user;
			}
		}

		return ret;
	}

	public UserDetails getUserDetails(String email) {
		UserDetails ud = null;
		Usuario user = dao.findByEmail(email);
		if (user != null) {
			ud = new CustomUserDetails(user);
		}
		return ud;
	}

	public Usuario createUsuario(String email, String password, String nome, String endereco, String telefone, String sexo, String sobre) {
		Usuario u = null;

		if (validateEmail(email) && dao.findByEmail(email) == null) {
			u = new Usuario();

			u.setEmail(email);
			u.setPassword(passwordEncoder.encode(password));
			u.setNome(nome);
			u.setEndereco(endereco);
			u.setTelefone(telefone);
			u.setSexo(sexo);
			u.setSobre(sobre);

			u.setAtivo(false);
			u.setHashAtivo(UUID.randomUUID().toString().replaceAll("-", ""));

			dao.saveAndFlush(u);
		}

		return u;
	}

	public Usuario activateUsuario(String id, String hash) {

		Usuario u = null;

		try {
			long parsedId = Long.parseLong(id);
			u = dao.findOne(parsedId);
		} catch (NumberFormatException e) {
		}

		if (u != null) {
			if (u.getHashAtivo().equals(hash)) {
				u.setAtivo(true);
				dao.saveAndFlush(u);
			} else {
				u = null;
			}
		}

		return u;
	}

}