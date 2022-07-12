package projeto.CF.api.resource;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import projeto.CF.api.dto.AbastecimentoDTO;
import projeto.CF.exception.ErroCriacaoRegistro;
import projeto.CF.model.entity.Abastecimento;
import projeto.CF.model.entity.Combustivel;
import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.AbastecimentoService;

@RestController
@RequestMapping("api/abastecimento")
public class AbastecimentoResource {

	private AbastecimentoService service;
	
	public AbastecimentoResource(AbastecimentoService service) {
		this.service = service;
	}
	
	@PostMapping("/salvar")
	public ResponseEntity Salvar(@RequestBody AbastecimentoDTO dto) {
		Usuario usuario,usuarioAlteracao;
		usuario = service.recuperaUsuario(dto.getUsuario());
		usuarioAlteracao = service.recuperaUsuario(dto.getUsuarioAlteracao());
		Combustivel comb = service.recuperaCombustivel(dto.getCombustivel());
		Local loc = service.recuperaLocal(dto.getLocal());
		Funcionario mot = service.recuperaFuncionario(dto.getMotorista());			
		Abastecimento abastecimento = Abastecimento.builder()
				.combustivel(comb)
				.local(loc)
				.motorista(mot)
				.litros(dto.getLitros())
				.dataAbastecimento(dto.getDataAbastecimento())
				.dataCadastro(dto.getDataCadastro())
				.kmRegistrado(dto.getKmRegistrado())
				.valor(dto.getValor())
				.usuario(usuario)
				.ativo(true)
				.usuarioAlteracao(usuarioAlteracao)
				.build();
		try {
			Abastecimento abastecimentoSalvo = service.salvarAbastecimento(abastecimento);
			return new ResponseEntity(abastecimentoSalvo, HttpStatus.CREATED);
		}
		catch(ErroCriacaoRegistro e) {
			return ResponseEntity.badRequest().body(e.getMessage());	
		}
	}
	
	@PostMapping("/ativar")
	public ResponseEntity ativar(@RequestBody AbastecimentoDTO dto) {
		Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		Abastecimento Alterado = service.findById(dto.getId());
		
		try {
			service.desativaAbastecimento(Alterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@PostMapping("/desativar")
	public ResponseEntity Desativar(@RequestBody AbastecimentoDTO dto) {
		Usuario Alterador = service.recuperaUsuario(dto.getUsuarioAlteracao());
		// TODO criar restrições de perfil
		//--------------------------------
		//--------------------------------
		Abastecimento Alterado = service.findById(dto.getId());
		
		try {
			service.desativaAbastecimento(Alterado);
			return ResponseEntity.ok(HttpStatus.ACCEPTED);
		}catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

}
