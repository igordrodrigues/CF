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

//import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table (name = "usuario", schema = "controle_frota")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Usuario {
	
	@Id
	@Column(name = "chapa")
	private Integer chapa;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "email")
	private String email;
	
	@Column(name = "senha")
	private String senha;
	
	@Column(name = "data_cadastro")
	@Convert(converter = Jsr310JpaConverters.LocalDateConverter.class)
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataCadastro; 
	
	@Column(name = "id_usuario")
	private Integer usuario;

	@Column(name = "id_usuario_alteracao")
	private Integer usuarioAlteracao;
	
	@Column (name="ativo")
	private boolean ativo;

	}
