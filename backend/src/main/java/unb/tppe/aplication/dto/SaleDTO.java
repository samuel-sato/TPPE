package unb.tppe.aplication.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import unb.tppe.domain.entity.Product;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class SaleDTO {
    private ClientDTO client;
    private SellerDTO seller;
    private LocalDate dateSale;
    private double price;
    private List<ProductDTO> products;
}
