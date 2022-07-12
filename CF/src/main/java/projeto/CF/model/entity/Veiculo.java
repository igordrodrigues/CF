package projeto.CF.model.entity;


import java.sql.Date;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
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
@Table(name= "veiculo", schema = "controle_frota")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Veiculo {
	
	@Id
	@Column (name = "placa")
	private String placa;
	
	@Column (name ="n_veiculo")
	private Integer numero;

	@Column (name ="n_chassi")
	private String chassi;
	
	@Column (name ="renavan")
	private Integer renavan;
	
	@Column (name ="ano")
	private Integer ano;

	@Column (name ="ativo")
	private boolean ativo;

	@ManyToOne
	@JoinColumn (name ="id_marca")
	private Marca marca;

	@ManyToOne
	@JoinColumn (name ="id_modelo")
	private Modelo modelo;

	@ManyToOne
	@JoinColumn (name ="id_tipo")
	private TipoEquipamento tipo;
	
	@ManyToOne
	@JoinColumn (name ="id_setor")
	private Setor setor;

	@ManyToOne
	@JoinColumn (name ="id_cor")
	private Cor cor;

	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCadastro; 
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@ManyToOne
	@JoinColumn(name = "id_usuario_alteracao")
	private Usuario usuarioAlteracao;
	


}
