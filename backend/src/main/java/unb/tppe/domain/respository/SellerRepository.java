package unb.tppe.domain.respository;

import unb.tppe.domain.entity.Seller;

public interface SellerRepository extends BaseRepository<Seller> {
    Seller findByIdPerson(Long idPerson);
}
