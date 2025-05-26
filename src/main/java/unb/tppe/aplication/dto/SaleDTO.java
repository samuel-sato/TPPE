package unb.tppe.aplication.dto;

import jakarta.enterprise.context.ApplicationScoped;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApplicationScoped
public class SaleDTO {
    private Long idClient;
    private Long idSeller;
    private List<Long> idsProduct;
}
