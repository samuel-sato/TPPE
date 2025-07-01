package unb.tppe.aplication.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.respository.UserRepository;
import unb.tppe.domain.useCase.LoginUseCase;

@ApplicationScoped
public class UserUseCaseProducer {

    private UserRepository userRepository;

    public UserUseCaseProducer(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @RequestScoped
    @Produces
    public LoginUseCase loginUseCase(){
        return new LoginUseCase(userRepository);
    }
}
