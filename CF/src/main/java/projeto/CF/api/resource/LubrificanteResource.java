package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.LubrificanteDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Lubrificante;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.LubrificanteService;

	@RestController
	@RequestMapping("api/lubrificante")
	public class LubrificanteResource {
		
			private LubrificanteService service;
			public LubrificanteResource (LubrificanteService service) {
				this.service = service;
			}
			
			@PostMapping("/salvar")
			public ResponseEntity Salvar(@RequestBody LubrificanteDTO dto) {
				Usuario usuario,usuarioAlteracao;
				usuario = service.recuperaUsuario(dto.getUsuario());
				usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


				Lubrificante tipoS = Lubrificante.builder()
						.nome(dto.getNome())
						.descricao(dto.getDescircao())
						.dataCadastro(dto.getDataCadastro())
						.usuario(usuario)
						.ativo(true)
						.usuarioAlteracao(usuarioAlteracao)
						.build();
				try {
					Lubrificante tipoSSalvo = service.salvarLubrificante(tipoS);
					return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
				}
				catch(ErroCriacaoRegistro e) {
					return ResponseEntity.badRequest().body(e.getMessage());
					
				}
			}
			@PostMapping("/ativar")
			public ResponseEntity ativar(@RequestBody LubrificanteDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Lubrificante Alterado = service.findById(dto.getId());
				
				try {
					service.desativaLubrificante(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

			@PostMapping("/desativar")
			public ResponseEntity Desativar(@RequestBody LubrificanteDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Lubrificante Alterado = service.findById(dto.getId());
				
				try {
					service.desativaLubrificante(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

}
