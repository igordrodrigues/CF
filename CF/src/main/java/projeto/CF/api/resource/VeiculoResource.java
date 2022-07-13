package projeto.CF.api.resource;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
				@GetMapping
				public ResponseEntity buscar(
						@RequestParam(value ="placa", required = false) String placa  ,
						@RequestParam(value ="ano", required = false) Integer ano  ,
						@RequestParam(value ="chassi", required = false)  String chassi,
						@RequestParam(value ="cor", required = false) Long idCor,
						@RequestParam(value ="marca", required = false) Long idMarca  ,
						@RequestParam(value ="modelo", required = false) Long idModelo ,
						@RequestParam(value ="numero", required = false) Integer numero ,
						@RequestParam(value ="renavan", required = false) Integer renavan ,
						@RequestParam(value ="setor", required = false) Long idSetor ,
						@RequestParam(value ="tipo", required = false)  Long idTipo,
						@RequestParam(value ="usuario", required = false) Integer idUsuario ,
						@RequestParam(value ="usuarioAlteracao", required = false) Integer idUsuarioAlteracao 
					) {
					
					Cor cor = new Cor();
					Marca marca = new Marca();
					Modelo modelo = new Modelo();
					Setor setor = new Setor();
					TipoEquipamento tipo = new TipoEquipamento();
					Usuario usuario = new Usuario();
					Usuario usuarioAlteracao = new Usuario();
					if(idCor != null) {cor = service.recuperaCor(idCor);}
					if(idMarca != null) {marca = service.recuperaMarca(idMarca);}
					if(idModelo != null) {modelo = service.recuperaModelo(idModelo);}
					if(idSetor != null) {setor = service.recuperaSetor(idSetor);}
					if(idTipo != null) {tipo = service.recuperaTipoEquipamento(idTipo);}
					if(idUsuario != null) {usuario = service.recuperaUsuario(idUsuario);}
					if(idUsuarioAlteracao != null) {usuarioAlteracao = service.recuperaUsuario(idUsuarioAlteracao);}
					Veiculo filtro = new Veiculo();
					filtro.setAno(ano);
					filtro.setChassi(chassi);
					filtro.setCor(cor);
					filtro.setMarca(marca);
					filtro.setModelo(modelo);
					filtro.setNumero(numero);
					filtro.setPlaca(placa);
					filtro.setRenavan(renavan);
					filtro.setSetor(setor);
					filtro.setTipo(tipo);
					filtro.setUsuario(usuario);
					filtro.setUsuarioAlteracao(usuarioAlteracao);;
					List<Veiculo> lista = service.buscaVeiculo(filtro);
					return ResponseEntity.ok(lista);
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
