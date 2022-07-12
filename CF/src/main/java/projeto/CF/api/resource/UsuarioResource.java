package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.UsuarioDTO;
import projeto.CF.exception.ErroAutenticacao;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.exception.ErroIdentificacaoRegistro;
import projeto.CF.exception.ErroInconsistenciaRegistro;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.UsuarioService;

@RestController
@RequestMapping("api/usuario")
public class UsuarioResource {

	private UsuarioService service;
	public UsuarioResource(UsuarioService service) {
		this.service = service;
	}
//	@GetMapping("/")
///	public String HelloWrod() {
//		return "hello word 2.0";
//	}
	
	@PostMapping("/autenticar")
	public ResponseEntity Autenticar(@RequestBody UsuarioDTO dto) {
		try {
			Usuario usuarioAutenticado = service.autenticar(dto.getChapa(),dto.getSenha());
			return ResponseEntity.ok(usuarioAutenticado);
		}catch (ErroAutenticacao e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		
	}
	
	@PostMapping("/salvar")
	public ResponseEntity Salvar(@RequestBody UsuarioDTO dto) {
		Usuario usuarioCadastro,usuarioAlteracao;
		usuarioCadastro = service.recuperaUsuario(dto.getUsuario());
		usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());

		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.dataCadastro(dto.getDataCadastro())
				.chapa(dto.getChapa())
				.usuario(usuarioCadastro.getChapa())
				.ativo(true)
				.usuarioAlteracao(usuarioAlteracao.getChapa())
				.build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
			
		}
		catch(ErroCriacaoRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}

	@PostMapping("/deletar")
	public ResponseEntity Deletar(@RequestBody UsuarioDTO dto) {
	    Usuario usuarioInformado = service.findById(dto.getChapa());
	    boolean userEquaslNome = usuarioInformado.getNome().equals(dto.getNome());
	    boolean userEquaslEmail = usuarioInformado.getEmail().equals(dto.getEmail());
		if(!(userEquaslNome) || !(userEquaslEmail)){
			throw new ErroInconsistenciaRegistro("Divergencia de dados entre registro salvo e informado!"); 
		}
	    
		try {
			service.deletarUsuario(usuarioInformado);
			return new ResponseEntity(HttpStatus.ACCEPTED);
			
		}
		catch(ErroIdentificacaoRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}

	@PostMapping("/alterar")
	public ResponseEntity Alterar(@RequestBody UsuarioDTO dto) {
		Usuario usuarioCadastro,usuarioAlteracao;
		usuarioCadastro = service.recuperaUsuario(dto.getUsuario());
		usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());

		Usuario usuario = Usuario.builder()
				.nome(dto.getNome())
				.email(dto.getEmail())
				.senha(dto.getSenha())
				.dataCadastro(dto.getDataCadastro())
				.chapa(dto.getChapa())
				.usuario(usuarioCadastro.getChapa())
				.usuarioAlteracao(usuarioAlteracao.getChapa())
				.build();
		
		try {
			Usuario usuarioSalvo = service.salvarUsuario(usuario);
			return new ResponseEntity(usuarioSalvo, HttpStatus.CREATED);
			
		}
		catch(ErroCriacaoRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());
			
		}
	}

	@PostMapping("/ativar")
	public ResponseEntity Ativar(@RequestBody UsuarioDTO dto) {
		Usuario usuarioAlterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		Usuario usuarioAlterado = service.findById(dto.getChapa());
		
		try {
			service.ativaUsuario(usuarioAlterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/desativar")
	public ResponseEntity Desativar(@RequestBody UsuarioDTO dto) {
		Usuario usuarioAlterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		Usuario usuarioAlterado = service.findById(dto.getChapa());
		
		try {
			service.desativaUsuario(usuarioAlterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
