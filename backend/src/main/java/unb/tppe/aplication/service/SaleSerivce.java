package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.ProductDTO;
import unb.tppe.aplication.dto.SaleDTO;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.entity.Client;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SaleRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.util.ArrayList;
import java.util.List;

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

        List<Product> productList = new ArrayList<>();

        for (int i=0; i<dto.getProducts().size(); i++){
            ProductDTO pDTO = dto.getProducts().get(i);

            productList.add(
                Product.builder()
                    .id(pDTO.getId())
                    .idDepartment(pDTO.getIdDepartment())
                    .department(pDTO.getDepartment())
                    .description(pDTO.getDescription())
                    .price(pDTO.getPrice())
                    .name(pDTO.getName())
                    .build());
        }

        Sale sale = Sale.builder()
            .seller(
                    Seller.builder()
                        .id(dto.getSeller().getId())
                        .numberHours(dto.getSeller().getNumberHours())
                        .baseSalary(dto.getSeller().getBaseSalary())
                        .person(Person.builder()
                            .name(dto.getSeller().getName())
                            .email(dto.getSeller().getEmail())
                            .build()
                        )
                        .build())
            .client(
                Client.builder()
                        .id(dto.getClient().getId())
                        .notifyPromotion(dto.getClient().getNotifyPromotion())
                        .person(Person.builder()
                                .name(dto.getClient().getName())
                                .email(dto.getClient().getEmail())
                                .build()
                        )
                        .build()
                    )
            .products(productList)
            .dateSale(dto.getDateSale())
            .price(dto.getPrice())
            .build();

        return createUseCase.execute(sale);
    }

    public List<Sale> listAll(){
        return readUseCase.listAll();
    }

    public Sale findById(Long id){
        return readUseCase.findById(id);
    }

    public Sale update(Long id, SaleDTO dto){

        List<Product> productList = new ArrayList<>();

        for (int i=0; i<dto.getProducts().size(); i++){
            ProductDTO pDTO = dto.getProducts().get(i);

            productList.add(
                    Product.builder()
                            .id(pDTO.getId())
                            .idDepartment(pDTO.getIdDepartment())
                            .department(pDTO.getDepartment())
                            .description(pDTO.getDescription())
                            .price(pDTO.getPrice())
                            .name(pDTO.getName())
                            .build());
        }

        Sale sale = Sale.builder()
                .id(id)
                .dateSale(dto.getDateSale())
                .price(dto.getPrice())
                .seller(
                        Seller.builder()
                                .id(dto.getSeller().getId())
                                .numberHours(dto.getSeller().getNumberHours())
                                .baseSalary(dto.getSeller().getBaseSalary())
                                .person(Person.builder()
                                        .name(dto.getSeller().getName())
                                        .email(dto.getSeller().getEmail())
                                        .build()
                                )
                                .build())
                .client(
                        Client.builder()
                                .id(dto.getClient().getId())
                                .notifyPromotion(dto.getClient().getNotifyPromotion())
                                .person(Person.builder()
                                        .name(dto.getClient().getName())
                                        .email(dto.getClient().getEmail())
                                        .build()
                                )
                                .build()
                )
                .products(productList)
                .build();

        return updateBseCase.execute(id, sale);
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }
}
