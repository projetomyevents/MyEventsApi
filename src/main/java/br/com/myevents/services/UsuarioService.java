package br.com.myevents.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.myevents.domain.Usuario;
import br.com.myevents.repository.UsuarioRepository;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UsuarioService {

    private final UsuarioRepository repository;

    private final BCryptPasswordEncoder pe;

    public boolean findEmail(String email) {
        Usuario user = repository.findByEmail(email);
        return user != null;
    }

    public Usuario insert(Usuario user) {
        user.setId(null);
        user.setSenha(pe.encode(user.getSenha()));
        return repository.save(user);
    }
    
}
