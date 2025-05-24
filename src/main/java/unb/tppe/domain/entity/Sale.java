package unb.tppe.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
public class Sale extends BaseEntity {
    private LocalDate dateSale;
    private double price;
}
