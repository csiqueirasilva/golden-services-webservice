package golden.services.model.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author csiqueira
 */
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {
}