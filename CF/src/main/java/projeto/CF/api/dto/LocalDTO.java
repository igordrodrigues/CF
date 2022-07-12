package projeto.CF.api.dto;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LocalDTO {
	private Long id;
	private String nome;
	private Long tipo;
	private Integer usuario;
	private Integer usuarioAlteracao;

	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
	
}
