package projeto.CF.service;

import java.util.List;

import projeto.CF.model.entity.Usuario;

public interface UsuarioService {

	Usuario autenticar(Integer chapa,String senha);
	Usuario salvarUsuario(Usuario usuario);
	Usuario findById(Integer chapa);
	Usuario recuperaUsuario(Integer id);
	Usuario alterarUsuario(Usuario usuario);
	List<Usuario> buscarUsuario(Usuario filtro);
	void deletarUsuario(Usuario usuario);
	void validarChapa(Integer chapa);
	void validarEmail(String email);
	void desativaUsuario(Usuario registro);
	void ativaUsuario(Usuario registro);
}
