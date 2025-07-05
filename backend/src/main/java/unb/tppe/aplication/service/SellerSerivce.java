package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.ClientDTO;
import unb.tppe.aplication.dto.SellerDTO;
import unb.tppe.domain.entity.Client;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class SellerSerivce {

    private CreateBaseUseCase<Seller, SellerRepository> createUseCase;
    private ReadBaseUseCase<Seller, SellerRepository> readUseCase;
    private DeleteBaseUseCase<Seller, SellerRepository> deleteBseCase;
    private UpdateBaseUseCase<Seller, SellerRepository> updateBseCase;


    public SellerSerivce(CreateBaseUseCase<Seller, SellerRepository> createUseCase,
                         ReadBaseUseCase<Seller, SellerRepository> readUseCase,
                         DeleteBaseUseCase<Seller, SellerRepository> deleteBseCase,
                         UpdateBaseUseCase<Seller, SellerRepository> updateBseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
        this.deleteBseCase = deleteBseCase;
        this.updateBseCase = updateBseCase;
    }

    public SellerDTO create(SellerDTO dto){
        Person p = Person.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .password(dto.getPassword())
            .birthdate(dto.getBirthdate())
            .build();

        Seller seller = Seller.builder()
            .person(p)
            .baseSalary(dto.getBaseSalary())
            .numberHours(dto.getNumberHours())
            .build();

        return mapper(createUseCase.execute(seller));
    }

    public List<SellerDTO> listAll(){
        return mapper(readUseCase.listAll());
    }

    public SellerDTO findById(Long id){
        return mapper(readUseCase.findById(id));
    }

    public SellerDTO update(Long id, SellerDTO dto){
        Person p = Person.builder()
                .id(id)
                .name(dto.getName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .password(dto.getPassword())
                .build();

        Seller seller = Seller.builder()
                .id(id)
                .person(p)
                .baseSalary(dto.getBaseSalary())
                .numberHours(dto.getNumberHours())
                .build();

        return mapper(updateBseCase.execute(id, seller));
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }

    private SellerDTO mapper(Seller seller){
        return SellerDTO.builder()
                .id(seller.getId())
                .name(seller.getPerson().getName())
                .email(seller.getPerson().getEmail())
                .password(seller.getPerson().getPassword())
                .birthdate(seller.getPerson().getBirthdate())
                .baseSalary(seller.getBaseSalary())
                .numberHours(seller.getNumberHours())
                .build();
    }

    private List<SellerDTO> mapper(List<Seller> sellers){
        return sellers.stream()
                .map(this::mapper)
                .collect(Collectors.toList());
    }
}
