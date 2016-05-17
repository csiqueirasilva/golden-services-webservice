package golden.services.model.trabalhos;

import golden.services.model.anuncios.Anuncio;
import golden.services.model.avaliacoes.Avaliacao;
import golden.services.model.usuarios.Usuario;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author csiqueira
 */
@Entity
public class Trabalho implements Serializable {

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datainicio;

    @Column
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date datafim;

    @Column
    private EstadoTrabalho estado;

    @OneToOne(optional = true, cascade = CascadeType.ALL)
    private Avaliacao avaliacao;

    @OneToOne(optional = false)
    private Anuncio anuncio;

    @OneToOne(optional = false)
    private Usuario usuario;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatainicio() {
        return datainicio;
    }

    public void setDatainicio(Date datainicio) {
        this.datainicio = datainicio;
    }

    public Date getDatafim() {
        return datafim;
    }

    public void setDatafim(Date datafim) {
        this.datafim = datafim;
    }

    public EstadoTrabalho getEstado() {
        return estado;
    }

    public void setEstado(EstadoTrabalho estado) {
        this.estado = estado;
    }

    public Avaliacao getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Avaliacao avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Anuncio getAnuncio() {
        return anuncio;
    }

    public void setAnuncio(Anuncio anuncio) {
        this.anuncio = anuncio;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
