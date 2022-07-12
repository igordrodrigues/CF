package projeto.CF.api.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AbastecimentoDTO {
	private Long id;
	private Long combustivel;
	private Integer kmRegistrado;
	private Integer motorista;
	private BigDecimal litros;
	private BigDecimal valor;
	private Long local;
	private Integer dia;
	private Integer ano;
	private Integer mes;
	private Integer usuario;
	private Integer usuarioAlteracao;
	
	public LocalDate getDataAbastecimento() {
		LocalDate retorno = LocalDate.of(ano,mes,dia);
		return retorno;
	}
	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}
