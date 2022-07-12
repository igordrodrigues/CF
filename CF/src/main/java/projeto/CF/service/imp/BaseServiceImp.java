package projeto.CF.service.imp;

import org.springframework.stereotype.Service;

import projeto.CF.model.entity.BaseRegistros;
import projeto.CF.model.entity.Usuario;
import projeto.CF.service.BaseService;

@Service
public class BaseServiceImp implements BaseService{

	@Override
	public BaseRegistros findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario recuperaUsuario(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void validarId(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleta(BaseRegistros registro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void desativa(BaseRegistros registro) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void ativa(BaseRegistros registro) {
		// TODO Auto-generated method stub
		
	}

}
