package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.PneusDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Pneus;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.PneuService;

@RestController
@RequestMapping("api/pneus")
public class PneusResource {
				private PneuService service;
				public PneusResource (PneuService service) {
					this.service = service;
				}
				
				@PostMapping("/salvar")
				public ResponseEntity Salvar(@RequestBody PneusDTO dto) {
					Usuario usuario,usuarioAlteracao;
					usuario = service.recuperaUsuario(dto.getUsuario());
					usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


					Pneus tipoS = Pneus.builder()
							.modelo(dto.getModelo())
							.marca(dto.getMarca())
							.dataCadastro(dto.getDataCadastro())
							.usuario(usuario)
							.ativo(true)
							.usuarioAlteracao(usuarioAlteracao)
							.build();
					try {
						Pneus tipoSSalvo = service.salvarPneus(tipoS);
						return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
					}
					catch(ErroCriacaoRegistro e) {
						return ResponseEntity.badRequest().body(e.getMessage());
						
					}
				}
				@PostMapping("/ativar")
				public ResponseEntity ativar(@RequestBody PneusDTO dto) {
					Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
					// TODO criar restrições de perfil
					//--------------------------------
					//--------------------------------
					Pneus Alterado = service.findById(dto.getId());
					
					try {
						service.desativaPneus(Alterado);
						return ResponseEntity.ok(HttpStatus.ACCEPTED);
					}catch (Exception e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}

				@PostMapping("/desativar")
				public ResponseEntity Desativar(@RequestBody PneusDTO dto) {
					Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
					// TODO criar restrições de perfil
					//--------------------------------
					//--------------------------------
					Pneus Alterado = service.findById(dto.getId());
					
					try {
						service.desativaPneus(Alterado);
						return ResponseEntity.ok(HttpStatus.ACCEPTED);
					}catch (Exception e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}

}
