package unb.tppe.infra.repository;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import unb.tppe.domain.entity.Product;
import unb.tppe.domain.respository.ProductRepository;
import unb.tppe.infra.mapping.ProductMapper;
import unb.tppe.infra.schema.ProductSchema;

import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductRepositorryImp implements ProductRepository, PanacheRepository<ProductSchema> {

    private ProductMapper mapper;

    public ProductRepositorryImp(ProductMapper mapper){
        this.mapper = mapper;
    }


    @Transactional
    public Product create(Product entity) {
        ProductSchema schema = mapper.toSchema(entity);
        persist(schema);
        return mapper.toDomain(schema);
    }


    public Optional<Product> listById(Long id) {
        Optional<ProductSchema> schemaOptional = findByIdOptional(id);

        return schemaOptional.map(productSchema -> mapper.toDomain(productSchema));
    }


    public List<Product> listAllEntity() {
        List<ProductSchema> ProductSchemas = listAll();
        return ProductSchemas.stream().map(mapper::toDomain).toList();
    }


    @Transactional
    public Product update(Product entity) {
        ProductSchema schema = findById(entity.getId());

        schema.setName(entity.getName());
        schema.setDescription(entity.getDescription());
        schema.setPrice(entity.getPrice());
        persist(schema);
        return mapper.toDomain(schema);
    }


    @Transactional
    public boolean deleteEntity(long id) {
        return deleteById(id);
    }
}
