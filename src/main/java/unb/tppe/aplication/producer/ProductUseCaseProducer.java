package unb.tppe.aplication.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.respository.ProductRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

@ApplicationScoped
public class ProductUseCaseProducer {

    private final ProductRepository repository;

    public ProductUseCaseProducer(ProductRepository repository){
        this.repository = repository;
    }

    @RequestScoped
    @Produces
    public CreateBaseUseCase<Product, ProductRepository> createUseCase(){
        return new CreateBaseUseCase<Product, ProductRepository>(repository);
    }

    @RequestScoped
    @Produces
    public ReadBaseUseCase<Product, ProductRepository> readUseCase(){
        return new ReadBaseUseCase<Product, ProductRepository>(repository);
    }

    @RequestScoped
    @Produces
    public DeleteBaseUseCase<Product, ProductRepository> deleteUseCase(){
        return new DeleteBaseUseCase<Product, ProductRepository>(repository);
    }

    @RequestScoped
    @Produces
    public UpdateBaseUseCase<Product, ProductRepository> updateUseCase(){
        return new UpdateBaseUseCase<Product, ProductRepository>(repository);
    }
}



















