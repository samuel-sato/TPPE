package unb.tppe.aplication.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Dependent;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.CreateSellerUseCase;
import unb.tppe.infra.schema.SellerSchema;

@ApplicationScoped
public class SellerUseCaseProducer {

    private final SellerRepository sellerRepository;

    public SellerUseCaseProducer(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }

    @RequestScoped
    @Produces
    public CreateBaseUseCase<Seller, SellerSchema, SellerRepository> createSellerUseCase(){
        return new CreateBaseUseCase<Seller, SellerSchema, SellerRepository>(sellerRepository);
    }
}



















