package projeto.CF.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MultaDTO {
	private Long id;
	private Integer usuario;
	private Integer motorista;
	private String localDaMulta;
	private String motivo;
	private String placa;
	private BigDecimal valor;
	private Integer ano;
	private Integer mes;
	private Integer dia;
	private Integer usuarioAlteracao;

	
	public LocalDate getDatamulta() {
		LocalDate retorno = LocalDate.of(ano,mes,dia);
		return retorno;
	}
	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}
