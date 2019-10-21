package br.com.myevents.services;

import org.springframework.security.core.context.SecurityContextHolder;

import br.com.myevents.security.UserSS;

public class UserService {

	public static UserSS authenticated() {
		try {
			//apenas retorna o usuário logado.
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}

}
