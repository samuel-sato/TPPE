package unb.tppe.domain.useCase;

import lombok.NoArgsConstructor;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.infra.schema.SellerSchema;


public class CreateSellerUseCase extends CreateBaseUseCase<Seller, SellerSchema, SellerRepository>{

    public CreateSellerUseCase() {
        super(null);
    }

    public CreateSellerUseCase(SellerRepository repository) {
        super(repository);
    }
}
