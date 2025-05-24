package unb.tppe.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class Person extends BaseEntity {
    private String name;
    private String email;
    private LocalDate birthdate;

//    public Person(){
//        super(null);
//    }
//
//    public Person(Long id, String name, String email, LocalDate birthdate){
//        super(id);
//        this.name = name;
//        this.email = email;
//        this.birthdate = birthdate;
//    }
}
