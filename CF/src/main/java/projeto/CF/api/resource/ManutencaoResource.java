package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.ManutencaoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.ManutencaoService;

@RestController
@RequestMapping("api/manutencao")
public class ManutencaoResource {
		
			private ManutencaoService service;

			public ManutencaoResource (ManutencaoService service) {
				this.service = service;
			}
			
			@PostMapping("/salvar")
			public ResponseEntity Salvar(@RequestBody ManutencaoDTO dto) {
				Usuario usuario,usuarioAlteracao;
				usuario = service.recuperaUsuario(dto.getUsuario());
				usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
				Funcionario func = service.recuperaMotorista(dto.getMotorista());
				Local loc = service.recuperaLocal(dto.getLocal());
				
				Manutencao tipoS = Manutencao.builder()
						.valor(dto.getValor())
						.motorista(func)
						.local(loc)
						.kmRegistrado(dto.getKmRegistrado())
						.dataManutencao(dto.getDataManutencao())
						.dataCadastro(dto.getDataCadastro())
						.usuario(usuario)
						.ativo(true)
						.usuarioAlteracao(usuarioAlteracao)
						.build();
				try {
					Manutencao tipoSSalvo = service.salvarManutencao(tipoS);
					return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
				}
				catch(ErroCriacaoRegistro e) {
					return ResponseEntity.badRequest().body(e.getMessage());
					
				}
			}
			@PostMapping("/ativar")
			public ResponseEntity ativar(@RequestBody ManutencaoDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Manutencao Alterado = service.findById(dto.getId());
				
				try {
					service.desativaManutencao(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

			@PostMapping("/desativar")
			public ResponseEntity Desativar(@RequestBody ManutencaoDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				Manutencao Alterado = service.findById(dto.getId());
				
				try {
					service.desativaManutencao(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

}
