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
public class VeiculoDTO {
	private Integer ano;
	private String chassi;
	private Long cor;
	private Long Marca;
	private Long Modelo;
	private Integer renavan;
	private Long setor;
	private Long tipo;
	private Integer numero;
	private String placa;
	private Integer usuario;
	private Integer usuarioAlteracao;

	
	public LocalDate getDataCadastro() {
		LocalDate retorno = LocalDate.now();
		return retorno;
	}
}
