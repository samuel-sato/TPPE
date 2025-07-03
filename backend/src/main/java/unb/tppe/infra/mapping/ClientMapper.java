package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Client;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.ClientSchema;

@ApplicationScoped
public class ClientMapper implements Mapping<Client, ClientSchema> {

    private PersonMapper personMapper;

    public ClientMapper(PersonMapper personMapper){
        this.personMapper = personMapper;
    }
    public Client toDomain(ClientSchema schema) {

        Person person = Person.builder()
                .id(schema.getPersonSchema().getId())
                .email(schema.getPersonSchema().getEmail())
                .birthdate(schema.getPersonSchema().getBirthdate())
                .name(schema.getPersonSchema().getName())
                .password(schema.getPersonSchema().getPassword())
                .build();

        return Client.builder()
                .id(schema.getId())
                .notifyPromotion(schema.isNotifyPromotion())
                .registrationDate(schema.getRegistrationDate())
                .person(person)
                .build();
    }


    public ClientSchema toSchema(Client entity) {
        return ClientSchema.builder()
                .personSchema(personMapper.toSchema(entity.getPerson()))
                .notifyPromotion(entity.isNotifyPromotion())
                .registrationDate(entity.getRegistrationDate())
                .build();
    }
}
