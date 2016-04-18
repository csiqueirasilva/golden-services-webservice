package golden.services.model.usuarios;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author csiqueira
 */
@Entity
public class Usuario implements Serializable {

	@Column
	private String nome;

	@Column
	private String endereco;

	@Column
	private String telefone;

	@Column
	private String sexo;

	@Column
	private String sobre;

	@Column
	private Boolean ativo;

	@Column
	private String hashAtivo;

	@Column(unique = true)
	private String email;

	@Column(length = 60)
	private String password;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return "SECURED";
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public String getHashAtivo() {
		return hashAtivo;
	}

	public void setHashAtivo(String hashAtivo) {
		this.hashAtivo = hashAtivo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getSobre() {
		return sobre;
	}

	public void setSobre(String sobre) {
		this.sobre = sobre;
	}

}
