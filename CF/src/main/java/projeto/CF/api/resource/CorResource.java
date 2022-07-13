package projeto.CF.api.resource;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
		
		@GetMapping
		public ResponseEntity buscar(
			@RequestParam(value ="id", required = false) Long id,
			@RequestParam(value = "nome", required = false) String nome,
//			@RequestParam(value ="ativo", required = false) boolean ativo ,
			@RequestParam(value ="dataCadastro", required = false) LocalDate cadastro ,
			@RequestParam(value ="usuario", required = false) Integer idUsuario,
			@RequestParam(value ="usuarioAltercao", required = false) Integer idUsuarioAlteracao
			) {
			
			Usuario usuario = new Usuario();
			Usuario usuarioAlteracao = new Usuario();
			if(idUsuario != null) {
				usuario = service.recuperaUsuario(idUsuario);
			}
			if(idUsuarioAlteracao != null) {
				usuario = service.recuperaUsuario(idUsuarioAlteracao);
			}
			
			Cor filtro = new Cor();
			if(nome != null && !nome.isEmpty()) {filtro.setNome(nome);}
	//		if(ativo) {filtro.setAtivo(ativo);}
			if(cadastro != null) {filtro.setDataCadastro(cadastro);}
			if(id!= null) {filtro.setId(id);}
			if(usuario != null) {filtro.setUsuario(usuario);}
			if(usuarioAlteracao!=null) {filtro.setUsuarioAlteracao(usuarioAlteracao);}
			List<Cor> lista = service.buscaCor(filtro);
			return ResponseEntity.ok(lista);
		}

}
