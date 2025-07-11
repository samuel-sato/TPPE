package unb.tppe.domain.useCase;

import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.respository.SaleRepository;

import java.util.List;

public class SaleUseCase extends ReadBaseUseCase<Sale, SaleRepository> {

    public SaleUseCase(SaleRepository repository){
        super(repository);
    }

    public List<Sale> findByIdClient(Long idClient) {
        return this.repository.findByIdClient(idClient);
    }
}
