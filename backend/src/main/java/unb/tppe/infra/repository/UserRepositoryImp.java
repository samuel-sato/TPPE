package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.respository.UserRepository;
import unb.tppe.infra.mapping.PersonMapper;
import unb.tppe.infra.schema.PersonSchema;

import java.util.Optional;

@ApplicationScoped
public class UserRepositoryImp implements UserRepository, PanacheRepository<PersonSchema> {

    PersonMapper mapper;

    public UserRepositoryImp(PersonMapper personMapper){
        this.mapper = personMapper;
    }

    public Optional<Person> findUserByEmail(String email) {
        Optional<PersonSchema> personSchemaOptional = find("email", email).firstResultOptional();

        if(personSchemaOptional.isPresent()){
            return Optional.of(mapper.toDomain(personSchemaOptional.get()));
        }
        return Optional.empty();
    }
}
