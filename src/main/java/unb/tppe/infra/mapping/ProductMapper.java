package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.ProductSchema;

@ApplicationScoped
public class ProductMapper implements Mapping<Product, ProductSchema> {

    public ProductMapper(){

    }

    public Product toDomain(ProductSchema schema) {

        return Product.builder()
                .id(schema.getId())
                .name(schema.getName())
                .price(schema.getPrice())
                .description(schema.getDescription())
                .build();
    }

    public ProductSchema toSchema(Product entity) {
        return ProductSchema.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .description(entity.getDescription())
                .build();
    }
}
