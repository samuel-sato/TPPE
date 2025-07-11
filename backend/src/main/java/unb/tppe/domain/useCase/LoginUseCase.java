package unb.tppe.domain.useCase;

import unb.tppe.domain.UserRoleEnum;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.User;
import unb.tppe.domain.respository.ClientRepository;
import unb.tppe.domain.respository.SellerRepository;
import unb.tppe.domain.respository.UserRepository;

import java.util.Optional;

public class LoginUseCase {

    private UserRepository userRepository;
    private ClientRepository clientRepository;
    private SellerRepository sellerRepository;

    public LoginUseCase(UserRepository userRepository,
                        ClientRepository clientRepository,
                        SellerRepository sellerRepository
    ){
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.sellerRepository = sellerRepository;
    }

    public Optional<User> getRoleUser(String email, String password){
        Optional<Person> optionalPerson = userRepository.findUserByEmail(email);

        if(optionalPerson.isPresent()) {
            if (optionalPerson.get().getPassword().equals(password)) {
                if (optionalPerson.get().getRole() == UserRoleEnum.CLIENT.value){
                    var client = clientRepository.findByIdPerson(optionalPerson.get().getId());
                    return Optional.ofNullable(User.builder()
                            .id(client.getId())
                            .name(client.getPerson().getName())
                            .role(client.getPerson().getRole())
                            .build());
                }
                if (optionalPerson.get().getRole() == UserRoleEnum.SELLER.value){
                    var seller = sellerRepository.findByIdPerson(optionalPerson.get().getId());
                    return Optional.ofNullable(User.builder()
                            .id(seller.getId())
                            .name(seller.getPerson().getName())
                            .role(seller.getPerson().getRole())
                            .build());
                }

                return Optional.of(
                        User.builder()
                                .id(optionalPerson.get().getId())
                                .role(optionalPerson.get().getRole())
                                .name(optionalPerson.get().getName())
                                .build()
                );
            }
        }

        return Optional.empty();
    }
}
