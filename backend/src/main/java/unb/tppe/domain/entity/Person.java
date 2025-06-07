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
}
