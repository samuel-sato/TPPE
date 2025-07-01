package unb.tppe.aplication.service;

import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import unb.tppe.aplication.dto.UserLoginDTO;
import unb.tppe.domain.entity.Person;
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

    public String login(UserLoginDTO user){

        Optional<Person> optionaPerson = loginUseCase.getRoleUser(user.getEmail(), user.getPassword());

        if(optionaPerson.isEmpty()){
            return "";
        }


        return generateToken(optionaPerson.get().getName(), optionaPerson.get().getRole());

    }

    private String generateToken(String userName, Integer roleNum) {
        String token = Jwt.issuer(ISSUER) // Emissor do token
                .upn(userName) // User Principal Name (geralmente o username ou ID do usuário)
                .groups(roleNum.toString()) // Roles do usuário como um Set de Strings. Use .name() para o nome da enum.
                .expiresIn(10L) // Token expira em 1 hora
                // Para chaves simétricas (HMAC)
                .signWithSecret(SECRET_KEY); // Assina com a chave secreta

        // Para chaves RSA (RECOMENDADO para produção, com privateKey carregada de forma segura)
//         .sign(privateKey); // Se você est

        return token;
    }
}
