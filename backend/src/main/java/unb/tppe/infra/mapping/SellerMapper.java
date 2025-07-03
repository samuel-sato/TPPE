package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.SellerSchema;

@ApplicationScoped
public class SellerMapper implements Mapping<Seller, SellerSchema> {

    private PersonMapper personMapper;

    public SellerMapper(PersonMapper personMapper){
        this.personMapper = personMapper;
    }
    public Seller toDomain(SellerSchema schema) {

        Person person = Person.builder()
                .id(schema.getPersonSchema().getId())
                .email(schema.getPersonSchema().getEmail())
                .birthdate(schema.getPersonSchema().getBirthdate())
                .name(schema.getPersonSchema().getName())
                .password(schema.getPersonSchema().getPassword())
                .build();

        return Seller.builder()
                .id(schema.getId())
                .baseSalary(schema.getBaseSalary())
                .numberHours(schema.getNumberHours())
                .person(person)
                .build();
    }


    public SellerSchema toSchema(Seller entity) {
        return SellerSchema.builder()
                .personSchema(personMapper.toSchema(entity.getPerson()))
                        .baseSalary(entity.getBaseSalary())
                .numberHours(entity.getNumberHours())
                .build();
    }
}
