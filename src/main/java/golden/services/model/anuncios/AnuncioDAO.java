package golden.services.model.anuncios;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author csiqueira
 */
public interface AnuncioDAO extends JpaRepository<Anuncio, Long> {
}