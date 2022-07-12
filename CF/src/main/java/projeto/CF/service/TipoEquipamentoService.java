package projeto.CF.service;

import java.util.List;

import projeto.CF.model.entity.TipoEquipamento;
import projeto.CF.model.entity.Usuario;

public interface TipoEquipamentoService {
	
	TipoEquipamento salvarTipoEquipamento(TipoEquipamento tipoEquipamento);
	TipoEquipamento findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarNome(String nome);
	List<TipoEquipamento> buscaTipoEquipamento(TipoEquipamento filtro);
	void deletaTipoEquipamento(TipoEquipamento registro);
	void desativaTipoEquipamento(TipoEquipamento registro);
	void ativaTipoEquipamento(TipoEquipamento registro);
}
