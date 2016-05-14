package golden.services.model.anuncios;

import golden.services.model.usuarios.Usuario;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author csiqueira
 */
@Entity
public class Anuncio implements Serializable {

    @ManyToOne
    private Usuario usuario;
    
    @Column
    private String areaDeAtuacao;

    @Column
    private TipoServico tipoDeServico;

    @Column(length = 5000)
    private String descricao;

    @Column
    private String regiao;

    @Column
    private BigDecimal preco;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAreaDeAtuacao() {
        return areaDeAtuacao;
    }

    public void setAreaDeAtuacao(String areaDeAtuacao) {
        this.areaDeAtuacao = areaDeAtuacao;
    }

    public TipoServico getTipoDeServico() {
        return tipoDeServico;
    }

    public void setTipoDeServico(TipoServico tipoDeServico) {
        this.tipoDeServico = tipoDeServico;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getRegiao() {
        return regiao;
    }

    public void setRegiao(String regiao) {
        this.regiao = regiao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
}
