package projeto.CF.service;

import java.util.List;

import projeto.CF.model.entity.TipoServico;
import projeto.CF.model.entity.Usuario;


public interface TipoServicoService {
	
	TipoServico salvarTipoServico(TipoServico tipoServico);
	TipoServico findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarNome(String nome);
	List<TipoServico> buscaTipoServico(TipoServico filtro);
	void deletaTipoSevico(TipoServico registro);
	void desativaTipoServico(TipoServico registro);
	void ativaTipoServico(TipoServico registro);
}
