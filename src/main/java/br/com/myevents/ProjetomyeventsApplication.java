package br.com.myevents;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.myevents.domain.Usuario;
import br.com.myevents.repository.UsuarioRepository;

@SpringBootApplication
public class ProjetomyeventsApplication implements CommandLineRunner {

	@Autowired
	private UsuarioRepository userRepo;
	
	@Autowired 
	BCryptPasswordEncoder pe;
	
	public static void main(String[] args) {
		SpringApplication.run(ProjetomyeventsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Usuario user = new Usuario();
		user.setEmail("gustavoheidemann@gmail.com");
		user.setCelular("82377832");
		user.setCpf("23423424323");
		user.setNome("Gustavo Heidemann");
		user.setSenha(pe.encode("123"));
		userRepo.save(user);
	}
}
