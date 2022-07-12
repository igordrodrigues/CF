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
@Table (name = "multa", schema = "controle_frota")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Multa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
			
	@Column(name = "data_multa")
	@Convert(converter = Jsr310JpaConverters.InstantConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataMulta;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.InstantConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCadastro;
	
	//@Column (name="km_registrado")
	//private Integer kmRegistrado;
	
	@Column(name = "local")
	private String local;
	
	@Column(name = "motivo")
	private String motivo;
	
	@ManyToOne
	@JoinColumn(name = "motorista")
	private Funcionario motorista;
		
	@Column(name = "valor")
	private BigDecimal valor;
		
//	@ManyToOne
//	@JoinColumn(name = "usuario")
//	private Usuario chapa;
	
	@ManyToOne
	@JoinColumn(name = "id_veiculo")
	private Veiculo placa;
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_usuario_alteracao")
	private Usuario usuarioAlteracao;

	
	@Column (name="ativo")
	private boolean ativo;

}