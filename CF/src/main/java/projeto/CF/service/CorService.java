package projeto.CF.service;


import java.util.List;

import projeto.CF.model.entity.Cor;
import projeto.CF.model.entity.Usuario;

public interface CorService {
	Cor salvarCor(Cor cor);
	Cor findById(Long id);
	Usuario recuperaUsuario(Integer id);
	void validarId(Long id);
	List<Cor> buscaCor(Cor filtro);
	void deletaCor(Cor registro);
	void desativaCor(Cor registro);
	void ativaCor(Cor registro);

}
