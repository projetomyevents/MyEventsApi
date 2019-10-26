package br.com.myevents.service;

import br.com.myevents.exception.CPFExistsException;
import br.com.myevents.exception.EmailExistsException;
import br.com.myevents.model.User;
import br.com.myevents.model.dto.NewUserDTO;
import br.com.myevents.repository.UserRepository;
import br.com.myevents.security.enums.Role;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementa a lógica de serviços de {@link User}.
 */
@Service
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * Registra um novo usuário validado. (não realiza validação de dados)
     *
     * @param newUser o novo usuário
     * @return um usuário registrado.
     * @throws EmailExistsException levantada sempre que um email já esteja vinculado a uma conta de usuário.
     * @throws CPFExistsException levantada sempre que um CPF já esteja vinculado a uma conta de usuário.
     */
    public User registerUser(NewUserDTO newUser) throws EmailExistsException {
        if (userRepository.findByEmail(newUser.getEmail()).isPresent()) {
            throw new EmailExistsException(
                    String.format("O email '%s' já está vinculado a uma conta de usuário.", newUser.getEmail()));
        }

        if (userRepository.findByCPF(newUser.getCpf()).isPresent()) {
            throw new CPFExistsException(
                    String.format("O CPF '%s' já está vinculado a uma conta de usuário.", newUser.getCpf()));
        }

        return userRepository.save(User.builder()
                .email(newUser.getEmail())
                .password(passwordEncoder.encode(newUser.getPassword()))
                .fullName(newUser.getFullName())
                .CPF(newUser.getCpf())
                .phoneNumber(newUser.getPhoneNumber())
                .role(Role.USUARIO.getId())
                .build());
    }

}
