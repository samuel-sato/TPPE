package unb.tppe.aplication.service;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.aplication.dto.ClientDTO;
import unb.tppe.domain.entity.Client;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.respository.ClientRepository;
import unb.tppe.domain.useCase.CreateBaseUseCase;
import unb.tppe.domain.useCase.DeleteBaseUseCase;
import unb.tppe.domain.useCase.ReadBaseUseCase;
import unb.tppe.domain.useCase.UpdateBaseUseCase;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ClientService {

    private CreateBaseUseCase<Client, ClientRepository> createUseCase;
    private ReadBaseUseCase<Client, ClientRepository> readUseCase;
    private DeleteBaseUseCase<Client, ClientRepository> deleteBseCase;
    private UpdateBaseUseCase<Client, ClientRepository> updateBseCase;


    public ClientService(CreateBaseUseCase<Client, ClientRepository> createUseCase,
                         ReadBaseUseCase<Client, ClientRepository> readUseCase,
                         DeleteBaseUseCase<Client, ClientRepository> deleteBseCase,
                         UpdateBaseUseCase<Client, ClientRepository> updateBseCase){
        this.createUseCase = createUseCase;
        this.readUseCase = readUseCase;
        this.deleteBseCase = deleteBseCase;
        this.updateBseCase = updateBseCase;
    }

    public Client create(ClientDTO dto){
        Person p = Person.builder()
            .name(dto.getName())
            .email(dto.getEmail())
            .birthdate(dto.getBirthdate())
            .build();

        Client seller = Client.builder()
            .person(p)
            .notifyPromotion(dto.getNotifyPromotion())
            .registrationDate(LocalDate.now())
            .build();

        return createUseCase.execute(seller);
    }

    public List<Client> listAll(){
        return readUseCase.listAll();
    }

    public Client findById(Long id){
        return readUseCase.findById(id);
    }

    public Client update(Long id, ClientDTO dto){
        Person p = Person.builder()
                .id(id)
                .name(dto.getName())
                .email(dto.getEmail())
                .birthdate(dto.getBirthdate())
                .build();

        Client seller = Client.builder()
                .id(id)
                .person(p)
                .notifyPromotion(dto.getNotifyPromotion())
                .build();

        return updateBseCase.execute(id, seller);
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }
}
