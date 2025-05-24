package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.PersonSchema;

@ApplicationScoped
public class PersonMapper implements Mapping<Person, PersonSchema> {

    public Person toDomain(PersonSchema schema) {

        Person p = Person.builder()
        .id(schema.getId())
                .name(schema.getName())
                .email(schema.getEmail())
                .birthdate(schema.getBirthdate()).build();

        return p;
    }

    public PersonSchema toSchema(Person entity) {
        return PersonSchema.builder()
                .id(entity.getId())
                .email(entity.getEmail())
                .name(entity.getName())
                .birthdate(entity.getBirthdate())
                .build();
    }
}
