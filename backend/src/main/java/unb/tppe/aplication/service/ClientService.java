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
import java.util.stream.Collectors;

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
            .password(dto.getPassword())
            .birthdate(dto.getBirthdate())
            .build();

        Client client = Client.builder()
            .person(p)
            .notifyPromotion(dto.getNotifyPromotion())
            .registrationDate(LocalDate.now())
            .build();

        return createUseCase.execute(client);
    }

    public List<ClientDTO> listAll(){
        return mapper(readUseCase.listAll());
    }

    public ClientDTO findById(Long id){
        return mapper(readUseCase.findById(id));
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

        var x = updateBseCase.execute(id, seller);
        System.out.println(x.toString());
        return x;
    }

    public boolean deleteById(Long id){
        return deleteBseCase.execute(id);
    }

    private ClientDTO mapper(Client client){
        return ClientDTO.builder()
                .id(client.getId())
                .name(client.getPerson().getName())
                .email(client.getPerson().getEmail())
                .birthdate(client.getPerson().getBirthdate())
                .password(client.getPerson().getPassword())
                .notifyPromotion(client.isNotifyPromotion())
                .build();
    }

    private List<ClientDTO> mapper(List<Client> clients){
        return clients.stream()
                .map(this::mapper)
                .collect(Collectors.toList());
    }
}
