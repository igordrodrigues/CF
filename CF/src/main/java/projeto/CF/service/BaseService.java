package projeto.CF.service;


import projeto.CF.model.entity.BaseRegistros;
import projeto.CF.model.entity.Usuario;

public interface BaseService {

	BaseRegistros findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	void deleta(BaseRegistros registro);
	void desativa(BaseRegistros registro);
	void ativa(BaseRegistros registro);


}
