package projeto.CF.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LubrificanteDTO {
	private Long id;
	private String nome;
	private String descircao;
	private Integer usuario;
	private Integer usuarioAlteracao;

	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}