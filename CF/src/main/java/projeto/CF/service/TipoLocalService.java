package projeto.CF.service;

import java.util.List;

import projeto.CF.model.entity.TipoLocal;
import projeto.CF.model.entity.Usuario;


public interface TipoLocalService {

	TipoLocal salvarTipoLocal(TipoLocal tipoLocal);
	TipoLocal findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarNome(String nome);
	List<TipoLocal> buscaTipoLocal(TipoLocal filtro);
	void deletaTipoLocal(TipoLocal registro);
	void desativaTipoLocal(TipoLocal registro);
	void ativaTipoLocal(TipoLocal registro);
}
