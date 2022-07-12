package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.PecasDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Pecas;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.PecasService;

@RestController
@RequestMapping("api/pecas")
public class PecasResource {
		
			private PecasService service;
			public PecasResource (PecasService service) {
				this.service = service;
			}
			
			@PostMapping("/salvar")
			public ResponseEntity Salvar(@RequestBody PecasDTO dto) {
				Usuario usuario,usuarioAlteracao;
				usuario = service.recuperaUsuario(dto.getUsuario());
				usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


				Pecas tipoS = Pecas.builder()
						.nome(dto.getNome())
						.descricao(dto.getDescricao())
						.valor(dto.getValor())
						.dataCadastro(dto.getDataCadastro())
						.usuario(usuario)
						.ativo(true)
						.usuarioAlteracao(usuarioAlteracao)
						.build();
				try {
					Pecas tipoSSalvo = service.salvarPecas(tipoS);
					return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
				}
				catch(ErroCriacaoRegistro e) {
					return ResponseEntity.badRequest().body(e.getMessage());
					
				}
			}
			@PostMapping("/ativar")
			public ResponseEntity ativar(@RequestBody PecasDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Pecas Alterado = service.findById(dto.getId());
				
				try {
					service.desativaPecas(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

			@PostMapping("/desativar")
			public ResponseEntity Desativar(@RequestBody PecasDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Pecas Alterado = service.findById(dto.getId());
				
				try {
					service.desativaPecas(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

}
