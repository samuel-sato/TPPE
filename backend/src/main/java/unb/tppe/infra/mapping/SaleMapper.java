package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Sale;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.SaleSchema;

import java.util.List;

@ApplicationScoped
public class SaleMapper implements Mapping<Sale, SaleSchema> {

    private ClientMapper clientMapper;
    private SellerMapper sellerMapper;
    private ProductMapper productMapper;

    public SaleMapper(ClientMapper clientMapper, SellerMapper sellerMapper, ProductMapper productMapper){
        this.clientMapper = clientMapper;
        this.sellerMapper = sellerMapper;
        this.productMapper = productMapper;
    }

    public Sale toDomain(SaleSchema schema) {

        return Sale.builder()
                .id(schema.getId())
                .client(clientMapper.toDomain(schema.getClient()))
                .seller(sellerMapper.toDomain(schema.getSeller()))
                .price(schema.getPrice())
                .dateSale(schema.getDateSale())
                .products(schema.getProducts().stream().map(p -> this.productMapper.toDomain(p)).toList())
                .build();
    }

    public List<Sale> toDomain(List<SaleSchema> schemas) {
        return schemas.stream().map(this::toDomain).toList();
    }


    public SaleSchema toSchema(Sale entity) {
        return SaleSchema.builder()
                .id(entity.getId())
                .dateSale(entity.getDateSale())
                .price(entity.getPrice())
                .build();
    }
}
