package golden.services.model.trabalhos;

import golden.services.model.usuarios.Usuario;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author csiqueira
 */
public interface TrabalhoDAO extends JpaRepository<Trabalho, Long> {
	@Query("SELECT t FROM Trabalho t WHERE t.anuncio.prestador = :prestador ORDER BY t.datafim DESC")
	List<Trabalho> findByPrestador(@Param("prestador") Usuario prestador);

	@Query("SELECT t FROM Trabalho t WHERE t.estado = golden.services.model.trabalhos.EstadoTrabalho.EFETUANDO and t.anuncio.prestador = :prestador")
	List<Trabalho> findByPrestadorEfetuando(@Param("prestador") Usuario prestador);
	
	@Query("SELECT t FROM Trabalho t WHERE (t.estado = golden.services.model.trabalhos.EstadoTrabalho.EFETUANDO or t.estado = golden.services.model.trabalhos.EstadoTrabalho.NAO_INICIADO) and t.usuario = :usuario")
	List<Trabalho> findByUsuarioEfetuando(@Param("usuario") Usuario usuario);
	
	@Query("SELECT t FROM Trabalho t WHERE t.estado = golden.services.model.trabalhos.EstadoTrabalho.ENCERRADO and t.usuario = :usuario and t.avaliacao = null")
	List<Trabalho> findByUsuarioEncerradoNaoAvaliado(@Param("usuario") Usuario usuario);
}