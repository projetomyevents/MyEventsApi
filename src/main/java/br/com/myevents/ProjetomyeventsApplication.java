package br.com.myevents;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.myevents.domain.Usuario;
import br.com.myevents.repository.UsuarioRepository;

@SpringBootApplication
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ProjetomyeventsApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjetomyeventsApplication.class, args);
    }

}
