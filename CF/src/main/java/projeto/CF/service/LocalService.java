package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Local;
import projeto.CF.model.entity.TipoLocal;
import projeto.CF.model.entity.Usuario;

public interface LocalService {

	Local salvarLocal(Local local);
	Local findById(Long id);
	TipoLocal recuperaTipoLocal(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Local> buscaLocal(Local filtro);
	void deletaLocal(Local registro);
	void desativaLocal(Local registro);
	void ativaLocal(Local registro);

}
