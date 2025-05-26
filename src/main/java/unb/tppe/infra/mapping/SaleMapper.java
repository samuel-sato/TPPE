package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Person;
import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.entity.Seller;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.ProductSchema;
import unb.tppe.infra.schema.SaleSchema;
import unb.tppe.infra.schema.SellerSchema;

@ApplicationScoped
public class SaleMapper implements Mapping<Sale, SaleSchema> {

    public Sale toDomain(SaleSchema schema) {

        return Sale.builder()
                .id(schema.getId())
                .idClient(schema.getClient().getId())
                .idSeller(schema.getSeller().getId())
                .price(schema.getPrice())
                .dateSale(schema.getDateSale())
                .idProducts(schema.getProducts().stream().map(ProductSchema::getId).toList())
                .build();
    }


    public SaleSchema toSchema(Sale entity) {
        return SaleSchema.builder()
                .id(entity.getId())
                .dateSale(entity.getDateSale())
                .price(entity.getPrice())
                .build();
    }
}
