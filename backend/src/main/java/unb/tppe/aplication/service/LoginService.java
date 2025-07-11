package unb.tppe.aplication.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.TokenDTO;
import unb.tppe.aplication.dto.UserLoginDTO;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.User;
import unb.tppe.domain.useCase.LoginUseCase;

import java.util.Optional;

@ApplicationScoped
public class LoginService {
    private static final String ISSUER = "quarkus-example";
    private static final String SECRET_KEY = "YourSuperSecureJwtSecretKeyWithAtLeast32CharactersLongAndComplexEnough!"; // 72 characters = 576 bits (more than enough)


    private LoginUseCase loginUseCase;

    public LoginService(LoginUseCase loginUseCase){
        this.loginUseCase = loginUseCase;
    }

    public TokenDTO login(UserLoginDTO user){

        Optional<User> optionaPerson = loginUseCase.getRoleUser(user.getEmail(), user.getPassword());
        TokenDTO dto = new TokenDTO();

        if(optionaPerson.isEmpty()){
            return dto;
        }

        String token = generateToken(optionaPerson.get().getId(), optionaPerson.get().getRole());
        dto.setToken(token);
        return dto;
    }

    private String generateToken(Long idUser, Integer roleNum) {
        String token = Jwt.issuer(ISSUER) // Emissor do token
                .upn(idUser.toString()) // User Principal Name (geralmente o username ou ID do usuário)
                .groups(roleNum.toString()) // Roles do usuário como um Set de Strings. Use .name() para o nome da enum.
                .expiresIn(1000L)
                // Para chaves simétricas (HMAC)
                .signWithSecret(SECRET_KEY); // Assina com a chave secreta

        // Para chaves RSA (RECOMENDADO para produção, com privateKey carregada de forma segura)
//         .sign(privateKey); // Se você est

        return token;
    }
}
