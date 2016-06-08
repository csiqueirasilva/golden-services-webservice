package golden.services.model.trabalhos.avaliacoes;

import golden.services.model.anuncios.Anuncio;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author csiqueira
 */
public interface AvaliacaoDAO extends JpaRepository<Avaliacao, Long> {
	@Query("SELECT a FROM Avaliacao a, Trabalho t WHERE t.avaliacao = a AND :anuncio = t.anuncio")
	List<Avaliacao> findByAnuncio(@Param("anuncio") Anuncio anuncio);
}