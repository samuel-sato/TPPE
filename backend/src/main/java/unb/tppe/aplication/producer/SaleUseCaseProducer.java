package unb.tppe.aplication.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.respository.SaleRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

@ApplicationScoped
public class SaleUseCaseProducer {

    private final SaleRepository repository;

    public SaleUseCaseProducer(SaleRepository repository){
        this.repository = repository;
    }

    @RequestScoped
    @Produces
    public CreateBaseUseCase<Sale, SaleRepository> createUseCase(){
        return new CreateBaseUseCase<Sale, SaleRepository>(repository);
    }

    @RequestScoped
    @Produces
    public ReadBaseUseCase<Sale, SaleRepository> readUseCase(){
        return new ReadBaseUseCase<Sale, SaleRepository>(repository);
    }

    @RequestScoped
    @Produces
    public DeleteBaseUseCase<Sale, SaleRepository> deleteUseCase(){
        return new DeleteBaseUseCase<Sale, SaleRepository>(repository);
    }

    @RequestScoped
    @Produces
    public UpdateBaseUseCase<Sale, SaleRepository> updateUseCase(){
        return new UpdateBaseUseCase<Sale, SaleRepository>(repository);
    }
}



















