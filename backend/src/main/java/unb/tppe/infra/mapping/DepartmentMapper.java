package unb.tppe.infra.mapping;

import jakarta.enterprise.context.ApplicationScoped;
import unb.tppe.domain.entity.Department;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.mapping.Mapping;
import unb.tppe.infra.schema.DepartmentSchema;

import java.util.List;

@ApplicationScoped
public class DepartmentMapper implements Mapping<Department, DepartmentSchema> {

    private ProductMapper productMapper;

    public DepartmentMapper(ProductMapper productMapper){
        this.productMapper = productMapper;
    }

    public Department toDomain(DepartmentSchema schema) {

        List<Product> productList = productMapper.toDomain(schema.getProducts());

        return Department.builder()
                .id(schema.getId())
                .name(schema.getName())
                .description(schema.getDescription())
                .products(productList)
                .build();
    }

    public DepartmentSchema toSchema(Department entity) {
        return DepartmentSchema.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .build();
    }
}
