package br.com.myevents.resources;

import java.net.URI;

import javax.validation.Valid;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.myevents.domain.Usuario;
import br.com.myevents.services.UsuarioService;

@RestController
@RequestMapping(value ="/cadastro-usuario")
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UsuarioResource {

    private final UsuarioService usuarioService;

    // Método utilizado para inserção
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> insert(@Validated @RequestBody Usuario usuario) {
        return ResponseEntity.created(
                ServletUriComponentsBuilder.fromCurrentRequest()
                        .path("/{id}")
                        .buildAndExpand(usuarioService.insert(usuario).getId()).toUri()
        ).build();
    }
}
