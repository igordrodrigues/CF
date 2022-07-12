package projeto.CF.api.resource;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.LocalDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.TipoLocal;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.LocalService;

@RestController
@RequestMapping("api/local")
public class LocalResource {
	
		private LocalService service;
		public LocalResource (LocalService service) {
			this.service = service;
		}
		
		@PostMapping("/salvar")
		public ResponseEntity Salvar(@RequestBody LocalDTO dto) {
			Usuario usuario,usuarioAlteracao;
			usuario = service.recuperaUsuario(dto.getUsuario());
			usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
			TipoLocal tipoLocal = service.recuperaTipoLocal(dto.getTipo());
			
			Local tipoS = Local.builder()
					.nome(dto.getNome())
					.tipo(tipoLocal)
					.dataCadastro(dto.getDataCadastro())
					.usuario(usuario)
					.ativo(true)
					.usuarioAlteracao(usuarioAlteracao)
					.build();
			try {
				Local tipoSSalvo = service.salvarLocal(tipoS);
				return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
			}
			catch(ErroCriacaoRegistro e) {
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
		}
		@PostMapping("/ativar")
		public ResponseEntity ativar(@RequestBody LocalDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			Local Alterado = service.findById(dto.getId());
			
			try {
				service.desativaLocal(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

		@PostMapping("/desativar")
		public ResponseEntity Desativar(@RequestBody LocalDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			Local Alterado = service.findById(dto.getId());
			
			try {
				service.desativaLocal(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}
