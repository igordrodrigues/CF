package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Marca;
import projeto.CF.model.entity.Usuario;

public interface MarcaService {

	Marca salvarMarca(Marca marca);
	Marca findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Marca> buscaMarca(Marca filtro);
	void deletaMarca(Marca registro);
	void desativaMarca(Marca registro);
	void ativaMarca(Marca registro);
}
