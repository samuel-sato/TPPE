package unb.tppe.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
public class Seller extends BaseEntity {
    private Person person;
    private double baseSalary;
    private double numberHours;
}
