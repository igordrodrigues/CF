package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Funcionario;
import projeto.CF.model.entity.Manutencao;
import projeto.CF.model.entity.OrdemServico;
import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;
import projeto.CF.model.entity.Veiculo;

public interface OrdemServicoService {

	OrdemServico salvarOrdemServico(OrdemServico ordemServico);
	OrdemServico findById(Long id);
	void validarId(Long id);
	Funcionario recuperaFuncionario(Integer id);
	Manutencao recuperaServico(Long id);
	TipoServico recuperaTipoServico(Long id);
	Usuario recuperaUsuario(Integer id);
	Veiculo recuperaVeiculo(String id);
	List<OrdemServico> buscaOrdemServico(OrdemServico filtro);
	void deletaOrdemServico(OrdemServico registro);
	void desativaOrdemServico(OrdemServico registro);
	void ativaOrdemServico(OrdemServico registro);
}
