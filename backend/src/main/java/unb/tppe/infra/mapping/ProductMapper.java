package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.ProductSchema;

import java.util.List;
import java.util.stream.Collectors;

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
                .idDepartment(schema.getDepartment() != null ? schema.getDepartment().getId() : null)
                .department(schema.getDepartment() != null ? schema.getDepartment().getName() : null)
                .build();
    }

    public List<Product> toDomain(List<ProductSchema> productSchemaList){
        return productSchemaList.stream().map(this::toDomain).toList();
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
