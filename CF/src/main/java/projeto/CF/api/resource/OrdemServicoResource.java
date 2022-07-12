package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.OrdemServicoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.OrdemServico;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;
import projeto.CF.service.OrdemServicoService;

@RestController
@RequestMapping("api/ordemServico")
public class OrdemServicoResource {
		
			private OrdemServicoService service;
			public OrdemServicoResource (OrdemServicoService service) {
				this.service = service;
			}
			
			@PostMapping("/salvar")
			public ResponseEntity Salvar(@RequestBody OrdemServicoDTO dto) {
				Usuario usuario,usuarioAlteracao;
				usuario = service.recuperaUsuario(dto.getUsuario());
				usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
				Funcionario func = service.recuperaFuncionario(dto.getFuncionario());
				Veiculo veiculo = service.recuperaVeiculo(dto.getPlaca());
				TipoServico tipoServico =service.recuperaTipoServico(dto.getTipoServico());
				Manutencao servico = service.recuperaServico(dto.getServico());
				
				OrdemServico tipoS = OrdemServico.builder()
						.dataFinal(dto.getDataFim())
						.dataInicio(dto.getDataInicio())
						.dataCadastro(dto.getDataCadastro())
						.funcionario(func)
						.hora_inicio(dto.getHoraInicio())
						.hora_fim(dto.getHoraFim())
						.servico(servico)
						.tipoServico(tipoServico)
						.veiculo(veiculo)
						.usuario(usuario)
						.ativo(true)
						.usuarioAlteracao(usuarioAlteracao)
						.build();
				try {
					OrdemServico tipoSSalvo = service.salvarOrdemServico(tipoS);
					return new ResponseEntity(tipoSSalvo, HttpStatus.CREATED);
				}
				catch(ErroCriacaoRegistro e) {
					return ResponseEntity.badRequest().body(e.getMessage());
					
				}
			}
			@PostMapping("/ativar")
			public ResponseEntity ativar(@RequestBody OrdemServicoDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				OrdemServico Alterado = service.findById(dto.getId());
				
				try {
					service.desativaOrdemServico(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

			@PostMapping("/desativar")
			public ResponseEntity Desativar(@RequestBody OrdemServicoDTO dto) {
				Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
				// TODO criar restrições de perfil
				//--------------------------------
				//--------------------------------
				OrdemServico Alterado = service.findById(dto.getId());
				
				try {
					service.desativaOrdemServico(Alterado);
					return ResponseEntity.ok(HttpStatus.ACCEPTED);
				}catch (Exception e) {
					return ResponseEntity.badRequest().body(e.getMessage());
				}
			}

}
