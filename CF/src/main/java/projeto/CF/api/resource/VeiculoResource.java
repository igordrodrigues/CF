package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.VeiculoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.exception.ErroInconsistenciaRegistro;
import projeto.CF.model.entity.Cor;
import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;
import projeto.CF.service.VeiculoService;

@RestController
@RequestMapping("api/veiculo")
public class VeiculoResource {

				private VeiculoService service;
				public VeiculoResource (VeiculoService service) {
					this.service = service;
				}
				
				@PostMapping("/salvar")
				public ResponseEntity Salvar(@RequestBody VeiculoDTO dto) {
					Usuario usuario,usuarioAlteracao;
					usuario = service.recuperaUsuario(dto.getUsuario());
					usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());

					Cor cor = service.recuperaCor(dto.getCor());
					Marca marca = service.recuperaMarca(dto.getMarca());
					Modelo modelo = service.recuperaModelo(dto.getModelo()); 
					Setor setor = service.recuperaSetor(dto.getSetor());
					TipoEquipamento tipo = service.recuperaTipoEquipamento(dto.getTipo());
					Veiculo tipoS = Veiculo.builder()
							.chassi(dto.getChassi())
							.dataCadastro(dto.getDataCadastro())
							.placa(dto.getPlaca())
							.cor(cor)
							.marca(marca)
							.modelo(modelo)
							.numero(dto.getNumero())
							.renavan(dto.getRenavan())
							.setor(setor)
							.tipo(tipo)
							.ano(dto.getAno())
							.ativo(true)
							.usuario(usuario)
							.usuarioAlteracao(usuarioAlteracao)
							.build();
					try {
						Veiculo tipoSSalvo = service.salvarVeiculo(tipoS);
						return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
					}
					catch(ErroCriacaoRegistro e) {
						return ResponseEntity.badRequest().body(e.getMessage());
						
					}
				}



				@PostMapping("/deletar")
				public ResponseEntity Deletar(@RequestBody VeiculoDTO dto) {
					Veiculo veiculo = service.findById(dto.getPlaca());
					boolean boolChassi = veiculo.getChassi().equals(dto.getChassi());
					boolean boolNumero = veiculo.getNumero().equals(dto.getNumero());
					boolean boolRenavan = veiculo.getRenavan().equals(dto.getRenavan());
					
					if(!(boolChassi || boolNumero || boolRenavan) ) {
						String msg ="";
						if(!(boolChassi) ) {msg.concat("Inconsistencia na informação sobre Chassi \n");}
						if(!(boolNumero) ) {msg.concat("Inconsistencia na informação sobre Numero do veiculo \n");}
						if(!(boolRenavan) ) {msg.concat("Inconsistencia na informação sobre Renavan \n");}
						throw new ErroInconsistenciaRegistro(msg);
					}
					try {
						service.deletarVeiculo(veiculo);
						return new ResponseEntity(HttpStatus.ACCEPTED);
					
					}
					catch(ErroIdentificacaoRegistro e) {
						return ResponseEntity.badRequest().body(e.getMessage());
						
					}
					
				}

				@PostMapping("/ativar")
				public ResponseEntity ativar(@RequestBody VeiculoDTO dto) {
					Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
					// TODO criar restrições de perfil
					//--------------------------------
					//--------------------------------
					Veiculo Alterado = service.findById(dto.getPlaca());
					
					try {
						service.desativaVeiculo(Alterado);
						return ResponseEntity.ok(HttpStatus.ACCEPTED);
					}catch (Exception e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}

				@PostMapping("/desativar")
				public ResponseEntity Desativar(@RequestBody VeiculoDTO dto) {
					Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
					// TODO criar restrições de perfil
					//--------------------------------
					//--------------------------------
					Veiculo Alterado = service.findById(dto.getPlaca());
					
					try {
						service.desativaVeiculo(Alterado);
						return ResponseEntity.ok(HttpStatus.ACCEPTED);
					}catch (Exception e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}

}
