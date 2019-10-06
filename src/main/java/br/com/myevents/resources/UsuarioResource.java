package br.com.myevents.resources;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.myevents.domain.Usuario;
import br.com.myevents.services.UsuarioService;

@RestController
@RequestMapping(value ="/cadastro-usuario")
public class UsuarioResource {
	
	UsuarioService userService;
	
	@RequestMapping(method = RequestMethod.POST) // POST: metódo utilziado para inserção
	public ResponseEntity<Void> insert(@Valid @RequestBody Usuario user) {
		user = userService.insert(user);			
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
		return ResponseEntity.created(uri).build();
	}
}
