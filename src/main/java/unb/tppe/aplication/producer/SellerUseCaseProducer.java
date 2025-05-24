package unb.tppe.aplication.producer;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.RequestScoped;
import jakarta.ws.rs.Produces;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.infra.schema.SellerSchema;

@ApplicationScoped
public class SellerUseCaseProducer {

    private final SellerRepository sellerRepository;

    public SellerUseCaseProducer(SellerRepository sellerRepository){
        this.sellerRepository = sellerRepository;
    }

    @RequestScoped
    @Produces
    public CreateBaseUseCase<Seller, SellerRepository> createUseCase(){
        return new CreateBaseUseCase<Seller, SellerRepository>(sellerRepository);
    }

    @RequestScoped
    @Produces
    public ReadBaseUseCase<Seller, SellerRepository> readUseCase(){
        return new ReadBaseUseCase<Seller, SellerRepository>(sellerRepository);
    }
}



















