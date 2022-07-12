package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.CorDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Cor;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.CorService;

@RestController
@RequestMapping("api/cor")
public class CorResource {


		private CorService service;
		public CorResource (CorService service) {
			this.service = service;
		}
		
		@PostMapping("/salvar")
		public ResponseEntity Salvar(@RequestBody CorDTO dto) {
			Usuario usuario,usuarioAlteracao;
			usuario = service.recuperaUsuario(dto.getUsuario());
			usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


			Cor corS = Cor.builder()
					.nome(dto.getNome())
					.dataCadastro(dto.getDataCadastro())
					.usuario(usuario)
					.ativo(true)
					.usuarioAlteracao(usuarioAlteracao)
					.build();
			try {
				Cor corSalvo = service.salvarCor(corS);
				return new ResponseEntity(corSalvo, HttpStatus.CREATED);
			}
			catch(ErroCriacaoRegistro e) {
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
		}
		@PostMapping("/ativar")
		public ResponseEntity ativar(@RequestBody CorDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			Cor Alterado = service.findById(dto.getId());
			
			try {
				service.desativaCor(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

		@PostMapping("/desativar")
		public ResponseEntity Desativar(@RequestBody CorDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			Cor Alterado = service.findById(dto.getId());
			
			try {
				service.desativaCor(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}
