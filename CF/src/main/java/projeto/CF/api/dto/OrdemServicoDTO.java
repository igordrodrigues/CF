package projeto.CF.api.dto;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@Builder
@Data
public class OrdemServicoDTO {
	private Long id;
	private Integer funcionario;
	private Long servico;
	private Long tipoServico;
	private Integer usuario;
	private String placa;
	private Integer ano_inicio;
	private Integer mes_inicio;
	private Integer dia_inicio;
	private Integer ano_fim;
	private Integer mes_fim;
	private Integer dia_fim;
	private Integer hora_inicio;
	private Integer minuto_inicio;
	private Integer hora_fim;
	private Integer minuto_fim;
	private Integer usuarioAlteracao;

	
	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
	public LocalDate getDataInicio() {
		LocalDate retorno = LocalDate.of(ano_inicio,mes_inicio,dia_inicio);
		return retorno;
	}
	public LocalDate getDataFim() {
		LocalDate retorno = LocalDate.of(ano_fim,mes_fim,dia_fim);
		return retorno;
	}
	public LocalTime getHoraInicio() {
		LocalTime retorno = LocalTime.of(hora_inicio, minuto_inicio);
		return retorno;
	}
	public LocalTime getHoraFim() {
		LocalTime retorno = LocalTime.of(hora_fim, minuto_fim);
		return retorno;
	}
}
