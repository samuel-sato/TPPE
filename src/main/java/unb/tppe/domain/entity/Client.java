package unb.tppe.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class Client extends BaseEntity {
    private Person person;
    private LocalDate registrationDate;
    private boolean notifyPromotion;
}
