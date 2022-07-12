package projeto.CF.model.entity;



import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name= "ordem_de_servico", schema = "controle_frota")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrdemServico {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column (name = "id")
	private Long id;
	
	@Column(name = "data_final")
	@Convert(converter = Jsr310JpaConverters.InstantConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFinal;
	
	@Column(name = "data_inicial")
	@Convert(converter = Jsr310JpaConverters.InstantConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataInicio;
	
	@Column(name = "horario_inicio")
	private LocalTime hora_inicio;
	
	
	@Column(name = "horario_final")
	private LocalTime hora_fim;
	
	
	@ManyToOne
	@JoinColumn (name ="id_veiculo")
	private Veiculo veiculo;

	@ManyToOne
	@JoinColumn (name ="id_responsavel")
	private Funcionario funcionario;

	@ManyToOne
	@JoinColumn (name ="id_usuario")
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn (name ="id_tipo_servico")
	private TipoServico tipoServico;

	@ManyToOne
	@JoinColumn (name ="id_manutencao")
	private Manutencao servico;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCadastro; 
	
	
	@ManyToOne
	@JoinColumn(name = "id_usuario_alteracao")
	private Usuario usuarioAlteracao;
	
	@Column (name="ativo")
	private boolean ativo;

}
