package unb.tppe.domain.respository;

import unb.tppe.domain.entity.Sale;

import java.util.List;

public interface SaleRepository extends BaseRepository<Sale> {
    List<Sale> findByIdClient(Long id);
}
