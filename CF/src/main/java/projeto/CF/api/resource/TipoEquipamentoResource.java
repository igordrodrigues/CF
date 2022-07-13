package projeto.CF.api.resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.TipoEquipamentoDTO;
import projeto.CF.api.dto.TipoServicoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.TipoEquipamentoService;
import projeto.CF.service.TipoServicoService;

@RestController
@RequestMapping("api/TipoEquipamento")
public class TipoEquipamentoResource {

		private TipoEquipamentoService service;
		public TipoEquipamentoResource (TipoEquipamentoService service) {
			this.service = service;
		}
		
		@PostMapping("/salvar")
		public ResponseEntity Salvar(@RequestBody TipoEquipamentoDTO dto) {
			Usuario usuario,usuarioAlteracao;
			usuario = service.recuperaUsuario(dto.getUsuario());
			usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


			TipoEquipamento tipoS = TipoEquipamento.builder()
					.nome(dto.getNome())
					.descricao(dto.getDescricao())
					.dataCadastro(dto.getDataCadastro())
					.ativo(true)
					.usuario(usuario)
					.usuarioAlteracao(usuarioAlteracao)
					.build();
			try {
				TipoEquipamento tipoSSalvo = service.salvarTipoEquipamento(tipoS);
				return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
			}
			catch(ErroCriacaoRegistro e) {
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
		}
		@PostMapping("/ativar")
		public ResponseEntity ativar(@RequestBody TipoEquipamentoDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			TipoEquipamento Alterado = service.findById(dto.getId());
			
			try {
				service.desativaTipoEquipamento(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

		@PostMapping("/desativar")
		public ResponseEntity Desativar(@RequestBody TipoEquipamentoDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			TipoEquipamento Alterado = service.findById(dto.getId());
			
			try {
				service.desativaTipoEquipamento(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

	}
