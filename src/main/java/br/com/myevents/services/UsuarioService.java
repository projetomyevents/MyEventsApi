package br.com.myevents.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myevents.domain.Usuario;
import br.com.myevents.repository.UsuarioRepository;

@Service
public class UsuarioService {
	
	@Autowired UsuarioRepository repository;
	
	@Autowired BCryptPasswordEncoder pe;
	
	public boolean findEmail(String email) {
		Usuario user = repository.findByEmail(email);
		if (user == null) {
			return false;
		} else {
			return true;
		}
	}
	
	public Usuario insert(Usuario user) {
		user.setId(null);
		user.setSenha(pe.encode(user.getSenha()));
		return repository.save(user);
	}

}
