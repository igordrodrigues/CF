package projeto.CF.model.entity;




import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;

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
@Table (name = "abastecimento", schema = "controle_frota")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Abastecimento {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "data_abastecimento")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataAbastecimento;
	
	@Column (name="km_registrado")
	private Integer kmRegistrado;
	
	@Column(name = "valor")
	private BigDecimal valor;
		
	@Column(name = "litros")
	private BigDecimal litros;

	@ManyToOne
	@JoinColumn(name = "id_posto")
	private Local local;

	@ManyToOne
	@JoinColumn(name = "id_motorista")
	private Funcionario motorista;

	@ManyToOne
	@JoinColumn(name = "id_combustivel")
	private Combustivel combustivel;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

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
