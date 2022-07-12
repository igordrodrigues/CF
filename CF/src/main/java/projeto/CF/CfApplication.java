package projeto.CF;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import projeto.CF.model.entity.Usuario;
import projeto.CF.model.repository.UsuarioRepository;

@SpringBootApplication
public class CfApplication {


	public static void main(String[] args) {
		SpringApplication.run(CfApplication.class, args);
		Usuario usuario =Usuario.builder().nome("usuario").email("a@a").build();

	}

}
