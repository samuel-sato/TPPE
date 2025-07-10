package unb.tppe.domain.useCase;

import unb.tppe.domain.entity.Person;
import unb.tppe.domain.respository.UserRepository;

import java.util.Optional;

public class LoginUseCase {

    private UserRepository userRepository;

    public LoginUseCase(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Optional<Person> getRoleUser(String email, String password){
        Optional<Person> optionalPerson = userRepository.findUserByEmail(email);

        if(optionalPerson.isPresent()) {
            if (optionalPerson.get().getPassword().equals(password)) {
                return optionalPerson;
            }
        }

        return Optional.empty();
    }
}
