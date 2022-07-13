package projeto.CF.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class UsuarioDTO {

	private String email;
	private String nome;
	private String senha;
	private Integer chapa;
	private Integer usuario;
	private Integer usuarioAlteracao;


	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}
