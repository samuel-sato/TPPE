package unb.tppe.aplication.producer;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.respository.ClientRepository;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.respository.UserRepository;
import unb.tppe.domain.useCase.LoginUseCase;

@ApplicationScoped
public class UserUseCaseProducer {

    private UserRepository userRepository;
    private SellerRepository sellerRepository;
    private ClientRepository clientRepository;

    public UserUseCaseProducer(
            UserRepository userRepository,
            SellerRepository sellerRepository,
            ClientRepository clientRepository
    ){
        this.userRepository = userRepository;
        this.sellerRepository = sellerRepository;
        this.clientRepository = clientRepository;
    }

    @RequestScoped
    @Produces
    public LoginUseCase loginUseCase(){
        return new LoginUseCase(userRepository, clientRepository, sellerRepository);
    }
}
