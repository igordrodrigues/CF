package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.SetorDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Setor;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.SetorService;

@RestController
@RequestMapping("api/setor")
public class SetorResource {
			
				private SetorService service;
				public SetorResource (SetorService service) {
					this.service = service;
				}
				
				@PostMapping("/salvar")
				public ResponseEntity Salvar(@RequestBody SetorDTO dto) {
					Usuario usuario,usuarioAlteracao;
					usuario = service.recuperaUsuario(dto.getUsuario());
					usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
					Funcionario func = service.recuperaResponsavel(dto.getId_responsavel());
					
					Setor tipoS = Setor.builder()
							.dataCadastro(dto.getDataCadastro())
							.responsavel(func)
							.nome(dto.getNome())
							.abreviatura(dto.getAbreviatura())
							.usuario(usuario)
							.ativo(true)
							.usuarioAlteracao(usuarioAlteracao)
							.build();
					try {
						Setor tipoSSalvo = service.salvarSetor(tipoS);
						return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
					}
					catch(ErroCriacaoRegistro e) {
						return ResponseEntity.badRequest().body(e.getMessage());
						
					}
				}
				@PostMapping("/ativar")
				public ResponseEntity ativar(@RequestBody SetorDTO dto) {
					Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
					// TODO criar restrições de perfil
					//--------------------------------
					//--------------------------------
					Setor Alterado = service.findById(dto.getId());
					
					try {
						service.desativaSetor(Alterado);
						return ResponseEntity.ok(HttpStatus.ACCEPTED);
					}catch (Exception e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}

				@PostMapping("/desativar")
				public ResponseEntity Desativar(@RequestBody SetorDTO dto) {
					Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
					// TODO criar restrições de perfil
					//--------------------------------
					//--------------------------------
					Setor Alterado = service.findById(dto.getId());
					
					try {
						service.desativaSetor(Alterado);
						return ResponseEntity.ok(HttpStatus.ACCEPTED);
					}catch (Exception e) {
						return ResponseEntity.badRequest().body(e.getMessage());
					}
				}

}
