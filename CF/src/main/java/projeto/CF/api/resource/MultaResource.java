package projeto.CF.api.resource;


import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.MultaDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.exception.RegraNegocioException;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Multa;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;
import projeto.CF.service.MultaService;

@RestController
@RequestMapping("api/multa")
public class MultaResource {
		
			private MultaService service;
			public MultaResource (MultaService service) {
				this.service = service;
			}
			
			@PostMapping("/salvar")
			public ResponseEntity Salvar(@RequestBody MultaDTO dto) {
				Usuario usuario,usuarioAlteracao;
				usuario = service.recuperaUsuario(dto.getUsuario());
				usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
				Funcionario func = service.recuperaFuncionario(dto.getMotorista()) ;
				Veiculo veiculo = service.recuperaVeiculo(dto.getPlaca());
				
				//verifica se o valor informado é maior que zero, caso não seja, lança um erro
				if(dto.getValor().compareTo(BigDecimal.ZERO) < 1) {
					throw new RegraNegocioException("Valor informado invalido");
				}				
				Multa tipoS = Multa.builder()
						.dataMulta(dto.getDatamulta())
						.dataCadastro(dto.getDataCadastro())
						.local(dto.getLocalDaMulta())
						.motivo(dto.getMotivo())
						.motorista(func)
						.placa(veiculo)
						.valor(dto.getValor())
						.usuario(usuario)
						.ativo(true)
						.usuarioAlteracao(usuarioAlteracao)
						.build();
				try {
					Multa tipoSSalvo = service.salvarMulta(tipoS);
					return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
				}
				catch(ErroCriacaoRegistro e) {
					return ResponseEntity.badRequest().body(e.getMessage());
					
				}
			}
			@PostMapping("/ativar")
			public ResponseEntity ativar(@RequestBody MultaDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Multa Alterado = service.findById(dto.getId());
				
				try {
					service.desativaMulta(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

			@PostMapping("/desativar")
			public ResponseEntity Desativar(@RequestBody MultaDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Multa Alterado = service.findById(dto.getId());
				
				try {
					service.desativaMulta(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

}
