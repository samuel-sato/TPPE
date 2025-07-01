package unb.tppe.domain.respository;

import unb.tppe.domain.entity.Person;

import java.util.Optional;

public interface UserRepository {
    Optional<Person> findUserByEmail(String email);
}
