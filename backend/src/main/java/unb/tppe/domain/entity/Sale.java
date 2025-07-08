package unb.tppe.domain.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@SuperBuilder
public class Sale extends BaseEntity {
    private LocalDate dateSale;
    private double price;
    private Client client;
    private Seller seller;
    private List<Product> products;
}
