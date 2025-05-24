package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.SellerDTO;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class SellerSerivce {

    private CreateBaseUseCase<Seller, SellerRepository> createUseCase;
    private ReadBaseUseCase<Seller, SellerRepository> readUseCase;


    public SellerSerivce(CreateBaseUseCase<Seller, SellerRepository> createUseCase,
                         ReadBaseUseCase<Seller, SellerRepository> readUseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
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

    public List<Seller> listAll(){
        return readUseCase.listAll();
    }

    public Optional<Seller> findById(Long id){
        return readUseCase.findById(id);
    }
}
