package projeto.CF.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.TipoLocalDTO;
import projeto.CF.api.dto.TipoServicoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.TipoLocal;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.TipoLocalService;
import projeto.CF.service.TipoServicoService;
@RestController
@RequestMapping("api/TipoLocal")
public class TipoLocalResource {


		private TipoLocalService service;
		public TipoLocalResource (TipoLocalService service) {
			this.service = service;
		}
		
		@PostMapping("/salvar")
		public ResponseEntity Salvar(@RequestBody TipoLocalDTO dto) {
			Usuario usuario,usuarioAlteracao;
			usuario = service.recuperaUsuario(dto.getUsuario());
			usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


			TipoLocal tipoS = TipoLocal.builder()
					.nome(dto.getNome())
					.descricao(dto.getDescricao())
					.dataCadastro(dto.getDataCadastro())
					.usuario(usuario)
					.ativo(true)
					.usuarioAlteracao(usuarioAlteracao)
					.build();
			try {
				TipoLocal tipoSSalvo = service.salvarTipoLocal(tipoS);
				return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
			}
			catch(ErroCriacaoRegistro e) {
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
		}
		@PostMapping("/ativar")
		public ResponseEntity ativar(@RequestBody TipoLocalDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			TipoLocal Alterado = service.findById(dto.getId());
			
			try {
				service.desativaTipoLocal(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

		@PostMapping("/desativar")
		public ResponseEntity Desativar(@RequestBody TipoLocalDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			TipoLocal Alterado = service.findById(dto.getId());
			
			try {
				service.desativaTipoLocal(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

	}
