package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.infra.schema.PersonSchema;

@ApplicationScoped
public class PersonRepositoryImp implements PanacheRepository<PersonSchema> {
}
