package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.SaleDTO;
import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.respository.SaleRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SaleSerivce {

    private CreateBaseUseCase<Sale, SaleRepository> createUseCase;
    private ReadBaseUseCase<Sale, SaleRepository> readUseCase;
    private DeleteBaseUseCase<Sale, SaleRepository> deleteBseCase;
    private UpdateBaseUseCase<Sale, SaleRepository> updateBseCase;


    public SaleSerivce(CreateBaseUseCase<Sale, SaleRepository> createUseCase,
                       ReadBaseUseCase<Sale, SaleRepository> readUseCase,
                       DeleteBaseUseCase<Sale, SaleRepository> deleteBseCase,
                       UpdateBaseUseCase<Sale, SaleRepository> updateBseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
        this.deleteBseCase = deleteBseCase;
        this.updateBseCase = updateBseCase;
    }

    public Sale create(SaleDTO dto){
        Sale seller = Sale.builder()
            .idSeller(dto.getIdSeller())
            .idClient(dto.getIdClient())
            .idProducts(dto.getIdsProduct())
            .build();

        return createUseCase.execute(seller);
    }

    public List<Sale> listAll(){
        return readUseCase.listAll();
    }

    public Sale findById(Long id){
        return readUseCase.findById(id);
    }

    public Sale update(Long id, SaleDTO dto){
        Sale seller = Sale.builder()
                .id(id)
                .idSeller(dto.getIdSeller())
                .idClient(dto.getIdClient())
                .idProducts(dto.getIdsProduct())
                .build();
        return updateBseCase.execute(id, seller);
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }
}
