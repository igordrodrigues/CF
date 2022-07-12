package projeto.CF.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PecasDTO {
	private Long id;
	private String nome;
	private String descricao;
	private BigDecimal valor;
	private Integer usuario;
	private Integer usuarioAlteracao;

	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}
