package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.TipoServicoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.TipoServicoService;

@RestController
@RequestMapping("api/tipoServico")
public class TipoServicoResource {

	private TipoServicoService service;
	public TipoServicoResource (TipoServicoService service) {
		this.service = service;
	}
	
	@PostMapping("/salvar")
	public ResponseEntity Salvar(@RequestBody TipoServicoDTO dto) {
		Usuario usuario,usuarioAlteracao;
		usuario = service.recuperaUsuario(dto.getUsuario());
		usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


		TipoServico tipoS = TipoServico.builder()
				.nome(dto.getNome())
				.descricao(dto.getDescricao())
				.dataCadastro(dto.getDataCadastro())
				.usuario(usuario)
				.ativo(true)
				.usuarioAlteracao(usuarioAlteracao)
				.build();
		try {
			TipoServico tipoSSalvo = service.salvarTipoServico(tipoS);
			return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
		}
		catch(ErroCriacaoRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	@PostMapping("/ativar")
	public ResponseEntity ativar(@RequestBody TipoServicoDTO dto) {
		Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		TipoServico Alterado = service.findById(dto.getId());
		
		try {
			service.desativaTipoServico(Alterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/desativar")
	public ResponseEntity Desativar(@RequestBody TipoServicoDTO dto) {
		Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		TipoServico Alterado = service.findById(dto.getId());
		
		try {
			service.desativaTipoServico(Alterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
