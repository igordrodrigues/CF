package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.ModeloDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Modelo;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.ModeloService;

@RestController
@RequestMapping("api/modelo")
public class ModeloResource {
		
			private ModeloService service;
			public ModeloResource (ModeloService service) {
				this.service = service;
			}
			
			@PostMapping("/salvar")
			public ResponseEntity Salvar(@RequestBody ModeloDTO dto) {
				Usuario usuario,usuarioAlteracao;
				usuario = service.recuperaUsuario(dto.getUsuario());
				usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
				Marca marc = service.recuperaMarca(dto.getMarca());
				
				Modelo tipoS = Modelo.builder()
						.nome(dto.getNome())
						.marca(marc)
						.abreviatura(dto.getAbreviatura())
						.dataCadastro(dto.getDataCadastro())
						.usuario(usuario)
						.ativo(true)
						.usuarioAlteracao(usuarioAlteracao)
						.build();
				try {
					Modelo tipoSSalvo = service.salvarModelo(tipoS);
					return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
				}
				catch(ErroCriacaoRegistro e) {
					return ResponseEntity.badRequest().body(e.getMessage());
					
				}
			}
			@PostMapping("/ativar")
			public ResponseEntity ativar(@RequestBody ModeloDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Modelo Alterado = service.findById(dto.getId());
				
				try {
					service.desativaModelo(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

			@PostMapping("/desativar")
			public ResponseEntity Desativar(@RequestBody ModeloDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Modelo Alterado = service.findById(dto.getId());
				
				try {
					service.desativaModelo(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

}
