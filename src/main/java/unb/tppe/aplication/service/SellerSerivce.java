package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.SellerDTO;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.infra.schema.SellerSchema;

@ApplicationScoped
public class SellerSerivce {

    private CreateBaseUseCase<Seller, SellerSchema, SellerRepository> createUseCase;

    public SellerSerivce(CreateBaseUseCase<Seller, SellerSchema, SellerRepository> createUseCase){
        this.createUseCase = createUseCase;
    }

    public Seller create(SellerDTO dto){

        Person p = Person.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .birthdate(dto.getBirthdate())
            .build();

        Seller seller = Seller.builder()
            .person(p)
            .baseSalary(dto.getBaseSalary())
            .numberHours(dto.getNumberHours())
            .build();

        return createUseCase.execute(seller);
    }
}
