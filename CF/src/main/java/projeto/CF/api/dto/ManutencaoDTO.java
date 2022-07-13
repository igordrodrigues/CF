package projeto.CF.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class ManutencaoDTO {
	private Long id;
	private Long local;
	private Integer motorista;
	private Integer ano;
	private Integer mes;
	private Integer dia;
	private BigDecimal valor;
	private Integer KmRegistrado;
	private Integer usuario;
	private Integer usuarioAlteracao;

	
	public LocalDate getDataManutencao() {
		LocalDate retorno = LocalDate.of(ano,mes,dia);
		return retorno;
	}
	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}
