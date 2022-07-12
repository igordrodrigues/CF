package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.CombustivelDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Combustivel;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.CombustivelService;

@RestController
@RequestMapping("api/combustivel")
public class CombustivelResource {

	private CombustivelService service;

	public CombustivelResource (CombustivelService service) {
		this.service = service;
	}
	
	@PostMapping("/salvar")
	public ResponseEntity Salvar(@RequestBody CombustivelDTO dto) {
		Usuario usuario,usuarioAlteracao;
		usuario = service.recuperaUsuario(dto.getUsuario());
		usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());

		
		Combustivel tipoS = Combustivel.builder()
				.nome(dto.getNome())
				.descricao(dto.getDescricao())
				.dataCadastro(dto.getDataCadastro())
				.usuario(usuario)
				.ativo(true)
				.usuarioAlteracao(usuarioAlteracao)
				.build();
		try {
			Combustivel tipoSSalvo = service.salvarCombustivel(tipoS);
			return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
		}
		catch(ErroCriacaoRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}
	@PostMapping("/ativar")
	public ResponseEntity ativar(@RequestBody CombustivelDTO dto) {
		Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		Combustivel Alterado = service.findById(dto.getId());
		
		try {
			service.desativaCombustivel(Alterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/desativar")
	public ResponseEntity Desativar(@RequestBody CombustivelDTO dto) {
		Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		Combustivel Alterado = service.findById(dto.getId());
		
		try {
			service.desativaCombustivel(Alterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
