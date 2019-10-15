package br.com.myevents.services;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.myevents.domain.Usuario;
import br.com.myevents.repository.UsuarioRepository;
import br.com.myevents.security.UserSS;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UsuarioRepository repository;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Usuario user = repository.findByEmail(email);
		if (user == null) {
			throw new UsernameNotFoundException(email);
		}
		return new UserSS(user.getId(),user.getEmail(),user.getSenha(), user.getPerfis());
	}
}
