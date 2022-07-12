package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.FuncionarioDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.FuncionarioService;

@RestController
@RequestMapping("api/funcionario")
public class FuncionarioResource {

		private FuncionarioService service;
		public FuncionarioResource (FuncionarioService service) {
			this.service = service;
		}
		
		@PostMapping("/salvar")
		public ResponseEntity Salvar(@RequestBody FuncionarioDTO dto) {
			Usuario usuario,usuarioAlteracao;
			usuario = service.recuperaUsuario(dto.getUsuario());
			usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());


			Funcionario tipoS = Funcionario.builder()
					.nome(dto.getNome())
					.dataCadastro(dto.getDataCadastro())
					.usuario(usuario)
					.ativo(true)
					.usuarioAlteracao(usuarioAlteracao)
					.build();
			try {
				Funcionario tipoSSalvo = service.salvarFuncionario(tipoS);
				return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
			}
			catch(ErroCriacaoRegistro e) {
				return ResponseEntity.badRequest().body(e.getMessage());
				
			}
		}
		@PostMapping("/ativar")
		public ResponseEntity ativar(@RequestBody FuncionarioDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			Funcionario Alterado = service.findById(dto.getChapa());
			
			try {
				service.desativaFuncionario(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

		@PostMapping("/desativar")
		public ResponseEntity Desativar(@RequestBody FuncionarioDTO dto) {
			Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
			// TODO criar restrições de perfil
			//--------------------------------
			//--------------------------------
			Funcionario Alterado = service.findById(dto.getChapa());
			
			try {
				service.desativaFuncionario(Alterado);
				return ResponseEntity.ok(HttpStatus.ACCEPTED);
			}catch (Exception e) {
				return ResponseEntity.badRequest().body(e.getMessage());
			}
		}

}
