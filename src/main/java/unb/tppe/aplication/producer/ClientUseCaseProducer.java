package unb.tppe.aplication.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.entity.Client;
import unb.tppe.domain.respository.ClientRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

@ApplicationScoped
public class ClientUseCaseProducer {

    private final ClientRepository repository;

    public ClientUseCaseProducer(ClientRepository repository){
        this.repository = repository;
    }

    @RequestScoped
    @Produces
    public CreateBaseUseCase<Client, ClientRepository> createUseCase(){
        return new CreateBaseUseCase<Client, ClientRepository>(repository);
    }

    @RequestScoped
    @Produces
    public ReadBaseUseCase<Client, ClientRepository> readUseCase(){
        return new ReadBaseUseCase<Client, ClientRepository>(repository);
    }

    @RequestScoped
    @Produces
    public DeleteBaseUseCase<Client, ClientRepository> deleteUseCase(){
        return new DeleteBaseUseCase<Client, ClientRepository>(repository);
    }

    @RequestScoped
    @Produces
    public UpdateBaseUseCase<Client, ClientRepository> updateUseCase(){
        return new UpdateBaseUseCase<Client, ClientRepository>(repository);
    }
}



















