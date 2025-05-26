package unb.tppe.infra.schema;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SALE")
public class SaleSchema {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "SALE_PRODUCT",
            joinColumns = @JoinColumn(name = "sale_id"),
            inverseJoinColumns = @JoinColumn(name = "product_id")
    )
    private List<ProductSchema> products;

    @ManyToOne(optional = false)
    @JoinColumn(name = "seller_id", referencedColumnName = "id")
    private SellerSchema seller;

    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private ClientSchema client;

    private LocalDate dateSale;
    private double price;
}
